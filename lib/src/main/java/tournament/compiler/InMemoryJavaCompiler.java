package tournament.compiler;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.util.*;

import javax.tools.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Getter;

@Service
public class InMemoryJavaCompiler {
	
	@Autowired
	private JavaCompiler javac;
	@Autowired
	@Getter private DynamicClassLoader classLoader;
	@Autowired
	private Instrumentation inst;
	@Autowired
	ExtendedStandardJavaFileManager fileManager;
	private Iterable<String> options;
	boolean ignoreWarnings = false;

	private Map<String, SourceCode> sourceCodes = new HashMap<String, SourceCode>();

	private Map<String, Class<?>> classes = new HashMap<String, Class<?>>();

	public InMemoryJavaCompiler useParentClassLoader(ClassLoader parent) {
		this.classLoader = new DynamicClassLoader(parent);
		return this;
	}
	
	public InMemoryJavaCompiler useOptions(String... options) {
		this.options = Arrays.asList(options);
		return this;
	}
	
	public InMemoryJavaCompiler ignoreWarnings() {
		ignoreWarnings = true;
		return this;
	}
	
	public InMemoryJavaCompiler addSource(String className, String sourceCode) throws Exception {
		sourceCodes.put(className, new SourceCode(className, sourceCode));
		return this;
	}
	
	public Class<?> compile(String className, String sourceCode) throws Exception {
		return addSource(className, sourceCode).compileAll().get(className);
	}
	
	public Map<String, Class<?>> compileAll() throws Exception {
		if (sourceCodes.size() == 0)
			throw new CompilationException("No source code to compile");
		
		DiagnosticCollector<JavaFileObject> collector = new DiagnosticCollector<>();
		JavaCompiler.CompilationTask task = javac.getTask(null, fileManager, collector, options, null, sourceCodes.values() );
		boolean result = task.call();
		if (!result || collector.getDiagnostics().size() > 0) 
			diagnoseResult(collector.getDiagnostics());

		Map<String, Class<?>> new_classes = new HashMap<String, Class<?>>();
		for (String className : sourceCodes.keySet()) {
			Class<?> cl = classLoader.loadClass(className);
			if(classes.get(className) == null)
				classes.put(className, cl);
			else
				inst.redefineClasses(new ClassDefinition(cl, classLoader.getByteCode(className) ) );
			
			new_classes.put(className, cl);
		}
		return new_classes;
	}
	
	public void diagnoseResult(List<Diagnostic<? extends JavaFileObject>> diagnostics) {
		StringBuffer exceptionMsg = new StringBuffer();
		exceptionMsg.append("Unable to compile the source");
		boolean hasWarnings = false;
		boolean hasErrors = false;
		for (Diagnostic<? extends JavaFileObject> d : diagnostics) {
			switch (d.getKind()) {
			case NOTE:
			case MANDATORY_WARNING:
			case WARNING:
				hasWarnings = true;
				break;
			case OTHER:
			case ERROR:
			default:
				hasErrors = true;
				break;
			}
			exceptionMsg.append("\n").append("[kind=").append(d.getKind());
			exceptionMsg.append(", ").append("line=").append(d.getLineNumber());
			exceptionMsg.append(", ").append("message=").append(d.getMessage(Locale.US)).append("]");
		}
		if (hasWarnings && !ignoreWarnings || hasErrors) {
			throw new CompilationException(exceptionMsg.toString());
		}
	}
	
}