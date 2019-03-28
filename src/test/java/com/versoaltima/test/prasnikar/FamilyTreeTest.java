package com.versoaltima.test.prasnikar;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import java.util.List;
import java.util.Arrays;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Tests for the FamilyTree class.
 * @author Neven Prašnikar (Dalo Lorn)
 * @version 28.03.2019
 */
public class FamilyTreeTest {
	// Streams used to capture console output, as per https://stackoverflow.com/a/1119559.
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;
	
	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}
	
	@After
	public void restoreStreams() {
		System.setOut(originalOut);
		System.setErr(originalErr);
	}

	/**
	 * Basic test based on the provided sample family.
	 */
	@Test
	public void testFamily() {
		List<String> vals = Arrays.asList(
			"Adam Ivan",
			"Marko Stjepan",
			"Stjepan Adam",
			"Robert Stjepan",
			"Fran Ivan",
			"Leopold Luka"
		);
		assertEquals(
			"Ivan\n" +
			"\tAdam\n" +
			"\t\tStjepan\n" +
			"\t\t\tMarko\n" +
			"\t\t\tRobert\n" +
			"\tFran\n" +
			"Luka\n" +
			"\tLeopold",
			new FamilyTree(vals).toString()
		);
	}

	/**
	 * Basic test based on the modified sample family I asked about.
	 */
	@Test
	public void testFamilyWithMultipleParents() {
		List<String> vals = Arrays.asList(
			"Adam Ivan",
			"Adam Luka",
			"Marko Stjepan",
			"Stjepan Adam",
			"Robert Stjepan",
			"Fran Ivan",
			"Leopold Luka"
		);
		assertEquals(
			"Ivan\n" +
			"\tAdam\n" +
			"\t\tStjepan\n" +
			"\t\t\tMarko\n" +
			"\t\t\tRobert\n" +
			"\tFran\n" +
			"Luka\n" +
			"\tAdam\n" +
			"\t\tStjepan\n" +
			"\t\t\tMarko\n" +
			"\t\t\tRobert\n" +
			"\tLeopold",
			new FamilyTree(vals).toString()
		);
	}

	/**
	 * Basic test designed to confirm that my error detection is working as intended.
	 */
	@Test
	public void testFamilyWithErrors() {
		List<String> vals = Arrays.asList(
			"Adam Ivan",
			"Ivan Adam",
			"Adam Adam",
			"Adam Adam Adam",
			"Marko Stjepan",
			"Stjepan Adam",
			"Robert Stjepan",
			"Fran Ivan",
			"Leopold Luka"
		);

		assertEquals( 
			"Ivan\n" +
			"\tAdam\n" +
			"\t\tStjepan\n" +
			"\t\t\tMarko\n" +
			"\t\t\tRobert\n" +
			"\tFran\n" +
			"Luka\n" +
			"\tLeopold",
			new FamilyTree(vals).toString()
		);
		
		assertEquals(
			String.format(
				"WARNING: Line \"Ivan Adam\" would result in an invalid family tree!%n" +
				"WARNING: Line \"Adam Adam\" would result in an invalid family tree!%n" +
				"WARNING: Line \"Adam Adam Adam\" is not a relationship string!%n"
			),
			errContent.toString()
		);
	}
}