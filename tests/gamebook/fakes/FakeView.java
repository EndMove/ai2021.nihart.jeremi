package gamebook.fakes;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.*;
import gamebook.utils.MethodCall;

class FakeView {
	private final Map<MethodCall, Integer> callsCount = new HashMap<>();

	public FakeView() {
		super();
	}

	protected void countCall(String methodName, Object...args) {
		var key = MethodCall.of(methodName, args);
		callsCount.putIfAbsent(key, 0);
		callsCount.computeIfPresent(key, (k,v) -> v + 1);
	}

	/**
	 * Vide la collection des appels re�us.
	 * Appelez cette m�thode quand vous souhaitez v�rifier qu'une action
	 * appelle une m�thode d�j� appel�e pr�c�demment.
	 * */
	public void resetCallsTrace() {
		callsCount.clear();
	}

	/**
	 * V�rifie que l'appel {@code methodeName(arg1, ..., argN)} a �t� re�u.
	 * Attention, l'ordre des arguments est important.
	 * 
	 * @param methodName le nom de la m�thode sans paranth�ses ni modificateur
	 * @param args les N arguments transmis au moment de l'appel.
	 * 
	 * @throws AssertionError si l'appel n'a pas �t� re�u.
	 * */
	public void verify(String methodName, Object...args) {
		verifyTimes(1, methodName, args);
	}

	/**
	 * V�rifie que l'appel {@code methodeName(arg)} a �t� re�u.
	 * La v�rification transforme la collection re�u en liste tri�e.
	 * Cette transformation permet v�rifier les appels sans tenir compte de la collection concr�te.
	 * 
	 * @param methodName le nom de la m�thode sans paranth�ses ni modificateur
	 * @param arg la collection de String.
	 * 
	 * @throws AssertionError si l'appel n'a pas �t� re�u.
	 * */
	public void verifyInAnyOrder(String methodName, Collection<? extends Object> arg) {
		var argAsList = new ArrayList<>(arg);
		argAsList.sort(Comparator.comparing(Object::toString));
		
		verifyTimes(1, methodName, argAsList);
	}

	private void verifyTimes(int expectedTimes, String methodName, Object...args) {
		var call = MethodCall.of(methodName, args);
		verifyAtLeastOne(call);
		final int times = callsCount.get(call);
		if(times != expectedTimes) {
			fail("Expected "+call+" to have been called "+expectedTimes+" times. Called "+times+".");
		}
	}

	private void verifyAtLeastOne(MethodCall call) {
		if(!callsCount.containsKey(call)) {
			fail("Expected "+call+" to have been called. No call found.");
		}
	}
	
	public void verifyAny(MethodCall call, MethodCall...other) {
		int count = callsCount.containsKey(call) ? 1 : 0;
		for(var c : other) {
			if(callsCount.containsKey(c)) {
				++count;
			}
		}
		if(count == 0) {
			fail("Expected of "+call+" or "+Arrays.toString(other)+" to have been called. No call found.");
		}
	}

}