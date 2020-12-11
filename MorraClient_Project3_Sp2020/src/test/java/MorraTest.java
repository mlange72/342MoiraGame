import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MorraTest {

	MorraInfo test = new MorraInfo();
	MorraInfo test2 = new MorraInfo();

	@Test
	void testP1Points(){
		test.setP1Points();
		assertEquals(1, test.getP1Points());
	}
	@Test
	void testP1Points2(){
		test.setP1Points();
		test.setP1Points();
		assertEquals(2, test.getP1Points());
	}

	@Test
	void testP2Points(){
		test.setP2Points();
		assertEquals(1, test.getP2Points());
	}

	@Test
	void testP2Points2(){
		test.setP2Points();
		test.setP2Points();
		assertEquals(2, test.getP2Points());
	}

	@Test
	void setPlayer(){
		test.setPlayerNum(1);
		assertEquals(1, test.getPlayerNum());
	}

	@Test
	void setP1Play(){
		test.setPlayerNum(1);
		assertEquals(1, test.getPlayerNum());
	}

	@Test
	void setP1Plays(){
		test.setPlayerNum(1);
		test.setPlay("5 7");
		assertEquals("5", test.getP1Count());
		assertNotEquals("4", test.getP1Count());
	}

	@Test
	void setP2Plays(){
		test.setPlayerNum(2);
		test.setPlay("4 7");
		assertNotEquals("4", test.getP1Plays());
		assertEquals("7", test.getP2Guess());
	}

	@Test
	void clear(){
		test.setP1Points();
		test.setP1Points();
		test.setP2Points();
		test.clear();
		assertEquals(0, test.getP1Points());
		assertEquals(0, test.getP2Points());
	}

	@Test
	void clear2(){
		test.setP1Points();
		test.setP1Points();
		test.setP2Points();
		test.clear();
		test.setP1Points();
		test.setP1Points();
		test.setP2Points();
		assertEquals(2, test.getP1Points());
		assertEquals(1, test.getP2Points());
	}

	@Test
	void totalTest(){
		test.setPlayerNum(1);
		test.setPlay("4 8");
		test.setPlayerNum(2);
		test.setPlay("5 6");
		test.setTotalNum();
		assertEquals(9, test.getTotalNum());
	}

	@Test
	void totalTest2(){
		test.setPlayerNum(1);
		test.setPlay("0 6");
		test.setPlayerNum(2);
		test.setPlay("0 8");
		test.setTotalNum();
		assertEquals(0, test.getTotalNum());
	}

	@Test
	void allTest(){
		test.setPlayerNum(1);
		assertEquals(1, test.getPlayerNum());
		test.setPlay("2 4");
		assertEquals("2", test.getP1Count());
		test.setPlayerNum(2);
		assertEquals(2, test.getPlayerNum());
		test.setPlay("0 10");
		assertEquals("10", test.getP2Guess());
		test.setTotalNum();
		test.setP1Points();
		assertEquals(1, test.getP1Points());
		test.setP2Points();
		assertEquals(1, test.getP2Points());
		assertEquals(2, test.getTotalNum());
	}

	@Test
	void allTest2(){
		test.setPlayerNum(1);
		test.setPlay("4 10");
		test.setPlayerNum(2);
		test.setPlay("6 8");
		test.setTotalNum();
		test.setP1Points();
		test.setP2Points();
		test.clear();
		assertEquals("4 10", test.getP1Plays());
		assertEquals("6 8", test.getP2Plays());
		assertEquals(0, test.getP1Points());
		assertEquals(0, test.getP2Points());
		assertEquals(10, test.getTotalNum());
	}

	@Test
	void twoClasses(){
		test.setPlayerNum(1);
		test.setPlay("4 6");
		test2.setPlayerNum(2);
		test2.setPlay("1 8");
		test2.setPlayerNum(test.getPlayerNum());
		test2.setPlay(test.getP1Plays());
		assertEquals("4", test2.getP1Count());
		assertEquals("1", test2.getP2Count());
	}

	@Test
	void duplicateClasses(){
		test.setPlayerNum(1);
		test.setPlay("5 8");
		test.setPlayerNum(2);
		test.setPlay("3 4");
		test.setP1Points();
		test.setP2Points();
		test.setP2Points();
		test.setTotalNum();
		test2 = test;
		assertEquals("8", test2.getP1Guess());
		assertEquals("4", test2.getP2Guess());
		assertEquals(1, test2.getP1Points());
		assertEquals(2, test2.getP2Points());
		assertEquals(8, test2.getTotalNum());
	}
}
