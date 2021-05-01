package IO;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SimpleDecompressorInputStream extends InputStream{
    private InputStream in;

    public SimpleDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {
        return 0;
    }

    @Override
    public int read(byte[] b) throws IOException {
        //Defining 8x Array to be on the same side (in case all the bits are different from their neighbours).
        byte [] tempArray = new byte[b.length*8];
        in.read(tempArray);
        ArrayList<Byte> byteList = decompress(tempArray);
        for (int i = 0; i < byteList.size(); i++) {
            b[i] = byteList.get(i);
            System.out.printf("Decompress Data[%d] = %x\n",i, b[i]);
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

        public void writeBits(int bitValue, int numberOfBits){
            //System.out.println("number of bits = "+ numberOfBits);
            for (int i=0; i<numberOfBits; i++){
                // update relevant bit (in bitCounter)
                byteBuffer |= bitValue << 7 - bitCounter;
                if (bitCounter == 7){
                    flush();
                }
                bitCounter ++;
            }
        }

        public void flush(){
            byte newByte = byteBuffer;
            if (bitCounter>0) {
                byteList.add(newByte);
                //System.out.println("adding to byte list");
                byteBuffer = 0x00;
                bitCounter = -1;
            }
        }

    }

    private ArrayList<Byte> decompress(byte [] byteArray) {
        int bitValue=0;
        ArrayList<Byte> byteList = new ArrayList<>();
        DecompressEngine decompressEngine = new DecompressEngine(byteList);;
        for (int i=0; i<byteArray.length; i++){
            decompressEngine.writeBits(bitValue, (int)byteArray[i]);
            bitValue = bitValue==0?1:0;
        }
        decompressEngine.flush();
        System.out.println("byteList.size = "+byteList.size());
        return byteList;
    }
}
