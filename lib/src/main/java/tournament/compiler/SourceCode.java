package tournament.compiler;

import javax.tools.SimpleJavaFileObject;

import lombok.Getter;

import java.io.IOException;
import java.net.URI;

public class SourceCode extends SimpleJavaFileObject {
	private String contents;
	@Getter private String className;

	public SourceCode(String className, String contents) throws Exception {
		super(URI.create("string:///" + className.replace('.', '/')
				+ Kind.SOURCE.extension), Kind.SOURCE);
		this.contents = contents;
		this.className = className;
	}
	
	@Override
	public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
		return contents;
	}
}