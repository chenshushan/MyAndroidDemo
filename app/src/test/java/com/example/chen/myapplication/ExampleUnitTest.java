package com.example.chen.myapplication;

import org.junit.Test;

import java.util.Scanner;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
	@Test
	public void addition_isCorrect()  {
		int seconds = 1600;
		int minutes = seconds / 60;
		int remainingSeconds = seconds % 60;
		System.out.println(seconds + " seconds is " + minutes +
				" minutes and "+ remainingSeconds + " seconds");

	}
}