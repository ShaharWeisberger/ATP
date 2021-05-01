package IO;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MyDecompressorInputStream extends InputStream {
    private InputStream in;

    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {
        return 0;
    }

    @Override
    public int read(byte[] b) throws IOException {
        //Defining 8x Array to be on the same side (in case all the bits are different from their neighbours).
        byte[] tempArray = new byte[b.length * 8];
        in.read(tempArray);
        ArrayList<Byte> byteList = decompress(tempArray);
        for (int i = 0; i < byteList.size(); i++) {
            b[i] = byteList.get(i);
            //System.out.printf("Decompress Data[%d] = %x\n", i, b[i]);
        }
        return byteList.size();
    }

    private class DecompressEngine {
        private ArrayList<Byte> byteList;
        private byte byteBuffer;
        private int bitCounter;

        public DecompressEngine(ArrayList<Byte> byteList) {
            this.byteList = byteList;
            bitCounter = 0;
            byteBuffer = 0x00;
        }

        //write when MSB equals to 1.
        public void readBits(int bitValue, int numberOfBits) {
            //System.out.println("number of bits = "+ numberOfBits);
            for (int i = 0; i < numberOfBits; i++) {
                // update relevant bit (in bitCounter)
                byteBuffer |= bitValue << 7 - bitCounter;
                if (bitCounter == 7) {
                    flush();
                }
                bitCounter++;
            }
        }

        //write when MSB equals to 0.
        private void readBitsAsIs(byte byteNum) {
            for (int i = 6; i >= 0; i--) {
                byteBuffer |= (byteNum << i) << 7 - bitCounter;
                if (bitCounter == 7) {
                    flush();
                }
                bitCounter++;
            }

        }
        public void flush() {
            byte newByte = byteBuffer;
            if (bitCounter > 0) {
                byteList.add(newByte);
                //System.out.println("adding to byte list");
                byteBuffer = 0x00;
                bitCounter = -1;
            }
        }
    }

    private int getBitValue(byte b, int index){
        return ((b >> index) & 1) == 1 ? 1 : 0;
    }

    private ArrayList<Byte> decompress(byte[] byteArray) {
        int bitValue = 0;
        byte sendByte ;
        ArrayList<Byte> byteList = new ArrayList<>();
        DecompressEngine decompressEngine = new DecompressEngine(byteList);
        for (int i = 0; i < byteArray.length; i++) {
            if (1 == getBitValue(byteArray[i], 7)){
                bitValue = getBitValue(byteArray[i], 6);
                //maybe a problem:
                sendByte = (byte)(127 & byteArray[i]);
                decompressEngine.readBits(bitValue, sendByte);
            }
            else{
                decompressEngine.readBitsAsIs(byteArray[i]);
            }
        }
        decompressEngine.flush();
        System.out.println("byteList.size = " + byteList.size());
        return byteList;
    }
}
