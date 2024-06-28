package com.ad.bloomfilter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class BloomfilterApplicationTests {

    private BloomFilter bloomFilter;

    @BeforeEach
    public void setup(){
        int expectedElements = 1000000; // Expected number of elements
        double falsePositiveProbability = 0.0001; // Desired false positive probability
        bloomFilter = new BloomFilter(expectedElements, falsePositiveProbability);
    }

    @Test
    void testAddAndCheck() {
        bloomFilter.add("AD");
        assertTrue(bloomFilter.check("AD"), "The Bloom filter should contain 'AD'");
        assertFalse(bloomFilter.check("Shanto"), "The Bloom filter should not contain 'Shanto'");
    }

    @Test
    public void testAddAllAndCheckAll(){
        List<String> wordList = new ArrayList<>();
        wordList.add("Khajar-pukur");
        wordList.add("balir-math");
        wordList.add("Shahid-minar");
        bloomFilter.addAll(wordList);
        assertTrue(bloomFilter.chechAll(wordList),"Should return true");
        wordList.add("Bonger-chad");
        assertFalse(bloomFilter.chechAll(wordList), "Should return false");
    }


}
