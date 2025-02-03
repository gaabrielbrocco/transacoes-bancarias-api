package com.java.transacoes_api.blockchain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Blockchain {

    private List<Block> chain;

    public Blockchain() {
        this.chain = new ArrayList<>();
        this.chain.add(createGenesisBlock());
    }

    public void addBlock(Map data, String tipo) {
        Block lastBlock = chain.get(chain.size() - 1);
        Block newBlock = new Block(lastBlock.getHash(), data, tipo);
        this.chain.add(newBlock);
    }

    public List<Block> getChain() {
        return this.chain;
    }

    public boolean isValid() {
        for (int i = 1; i < chain.size(); i++) {
            Block currentBlock = chain.get(i);
            Block previousBlock = chain.get(i - 1);
            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }
            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                return false;
            }
        }
        return true;
    }

    private Block createGenesisBlock() {
        Map<String, Object> data = new HashMap<>();
        data.put("data", "Genesis Block");

        return new Block("0", data, "Genesis");
    }
}
