package com.ad.bloomfilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;
import java.util.Collection;

public class BloomFilter {
    private static final Logger logger = LoggerFactory.getLogger(BloomFilter.class);
    private BitSet bitSet;
    private int size;
    private int hashCount;

    public BloomFilter(int expectedElements, double probaFalsePositive) {

        this.size = (int) Math.ceil(-expectedElements * Math.log(probaFalsePositive) / (Math.pow(Math.log(2),2)));
        this.hashCount = (int) Math.ceil((size / (double) expectedElements) * Math.log(2));
        this.bitSet = new BitSet(size);
        logger.info("size = " + size);
        logger.info("hashcount = " + hashCount);
    }
    public void add(String value){
        int[] hashes = getHashes(value);
        for(int hash : hashes){
            bitSet.set(hash);
            //logger.info("adding" + value);
        }
    }
    public void addAll(Collection<String> collection){
        for (String t : collection){
            add(t);
        }
    }

    public boolean check(String value){
        int[] hashes = getHashes(value);
        for(int hash : hashes){
            if(!bitSet.get(hash)){
                return  false;
            }
        }
        return  true;
    }

    public boolean chechAll (Collection<String> collection){
        for (String t : collection){
            if(! check(t)){
                return  false;
            }
        }
        return  true;
    }

    public void clear(){
        bitSet.clear();
    }

    private int[] getHashes(String value){
        int [] hashes = new int[hashCount];
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            for (int i = 0; i < hashCount; i++){
                messageDigest.update((value+ i).getBytes());
                byte[] digest = messageDigest.digest();
                int hashValue = Math.abs(bytesToInt(digest)) % size;
                hashes[i] = hashValue;
            }

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return  hashes;
    }

    private int bytesToInt(byte[] bytes) {
        int result = 0;
        for(int i = 0; i < Math.min(bytes.length,4); i++){
            result <<= 8;
            result |= (bytes[i] & 0xFF);
        }

        return result;
    }

}
