package gamebook.utils;

import java.util.Arrays;
import java.util.stream.*;

public class MethodCall {
	private final String methodName;
	private final Object[] args;
	
	private MethodCall(String methodName, Object[] args) {
		this.methodName = methodName;
		this.args = Arrays.copyOf(args, args.length);
	}
	
	public static MethodCall of(String methodName,Object...args) {
		return new MethodCall(methodName, args);
	}
	
	@Override
	public String toString() {
		return Stream.of(args)
					.map(arg -> arg.toString() )
					.collect(Collectors.joining(",", methodName+"(",")"));
	}
	
	@Override
	public int hashCode() {
		return methodName.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) {
			return true;
		}
		if(!(o instanceof MethodCall)) {
			return false;
		}
		
		return this.toString().equals(o.toString());
	}
}
