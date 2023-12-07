package br.ufrpe.poo.banco.negocio;

import static org.mockito.Mockito.*;

import java.util.List;

public class TesteMockito {

	public static void main(String[] args) {
		// mock creation
		List<String> mockedList = mock(List.class);
		System.out.println(mockedList.add("abc"));
		System.out.println(mockedList.toString());
		// or even simpler with Mockito 4.10.0+
		// List mockedList = mock();

		// using mock object - it does not throw any "unexpected interaction" exception
		mockedList.add("one");
		mockedList.clear();

		// selective, explicit, highly readable verification
		verify(mockedList).add("one");
		verify(mockedList).clear();

	}

}
