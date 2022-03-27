package tournament.config;

import java.lang.instrument.Instrumentation;

public class Agent {
	
	public static Instrumentation instrument;
	
	public static void premain(String args, Instrumentation inst) {
		instrument = inst;
	}
}
