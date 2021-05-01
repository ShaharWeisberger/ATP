package IO;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class MyCompressorOutputStream extends OutputStream {

    private OutputStream out;

    public MyCompressorOutputStream(OutputStream out) {
        this.out = out;
    }

    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        out.write(compress(b));
    }

    //  first bit == 1 , look at second bit (0 or 1) --> the other bits (3-8) are number of repetition (up to 63 (+6))
    //  first bit == 0 --> the other bits are taken as is

    // 11100001 -> (33)   11111111111111111111111111111111..
    // 10100001 -> (33)   00000000000000000000000000000000..

    // 01010101 -> 1010101

    private class CompressEngine {
        private ArrayList<Byte> byteList;
        private byte byteBuffer;
        private int bitCounter;
        private int count, prev, asIsBitCount;
        private byte asIsByte;
        private boolean possibleChain;

        public CompressEngine(ArrayList<Byte> byteList) {
            this.byteList = byteList;
            bitCounter = 0;
            byteBuffer = 0x00;
            count = 0;
            prev = 0;
            asIsBitCount = 0;
            asIsByte = 0x00;
            possibleChain = true;
        }

        private void updateAsIsByte(int value){
            asIsBitCount ++;
            // 010000000
            // 011000000
            // 011000000
            // 011010000
            if (asIsBitCount<=7){
                asIsByte |= 1 << 7 - asIsBitCount;
                //put  value in asIsBitCount location in asIsByte
            }
            // need to implement
        }

        public void putBit(int bitValue) {
            updateAsIsByte(bitValue);
            if (bitValue == prev && possibleChain) {
                count++;
                if (count == 63) {
                    flush();
                }
            }
            else {
                possibleChain = false;
                if (count >=7 || asIsBitCount == 7) {
                    flush();
                }
                if (count>=7){
                    updateAsIsByte(bitValue);
                }
                prev = bitValue;
            }
        }

        //write when MSB equals to 1.
        public byte createRepeatingByte(int bitValue, int numberOfBits) {
            byte b = (byte)128;
            if (bitValue == 1){
                b += (byte)64;
            }
            b += (byte) numberOfBits;
            return b;
        }

        public void flush() {
            System.out.println("flush was called ---------");
            byte newByte = byteBuffer;
            if (count >= 7) {
                newByte = createRepeatingByte(prev, count);
                byteList.add(newByte);
            } else {
                if (asIsBitCount > 0) {
                    newByte = asIsByte;
                    byteList.add(newByte);
                }
            }
            count = 0;
            asIsByte = 0x00;
            possibleChain = true;
            asIsBitCount = 0;
        }
    }

    private int getBitValue(byte b, int index){
        return ((b >> index) & 1) == 1 ? 1 : 0;
    }

    private byte[] compress(byte[] byteArray) {
        int bitValue;
        ArrayList<Byte> byteList = new ArrayList<>();
        CompressEngine compressEngine = new CompressEngine(byteList);
        // 00011100 00000000 00001011 11111111 11110101 11111111
        for (int i = 0; i < byteArray.length; i++) {
            System.out.printf("byteArray[%d] = %x\n",i, byteArray[i]);
            for (int j = 7; j >= 0; j--) {
                bitValue = getBitValue(byteArray[i], j);
                compressEngine.putBit(bitValue);
            }
        }
        compressEngine.flush();
        byte[] data = new byte[byteList.size()];
        for (int i = 0; i < data.length; i++) {
            data[i] = byteList.get(i);
            System.out.printf("Data[%d] = %s\n",i, Integer.toBinaryString((int)data[i]));
        }
        return data;
    }

    /*
    private void addCountToByteList(ArrayList<Byte> byteList, int count, int bitValue) {
        byte tempByte= (byte)(69+128);
        while (count >= 70) {
            tempByte |= bitValue << 6;
            byteList.add(tempByte);
            byteList.add((byte) 0);
            count -= 69;
        }
        tempByte = (byte)(count+128);
        tempByte |= bitValue << 6;
        byteList.add(tempByte);
    }
     */
}

