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
        int size = in.read(tempArray);
        ArrayList<Byte> byteList = decompress(tempArray, size);
        for (int i = 0; i < byteList.size(); i++) {
            b[i] = byteList.get(i);
            //System.out.printf("Decompress Data[%d] = %x\n",i, b[i]);
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
            for (int i=0; i<numberOfBits; i++){
                byteList.add((byte)bitValue);
            }
        }
    }

    private ArrayList<Byte> decompress(byte [] byteArray, int size) {
        int value=0;
        ArrayList<Byte> byteList = new ArrayList<>();
        DecompressEngine decompressEngine = new DecompressEngine(byteList);;
        int asIsBytes = getIntFrom2ByteArray(byteArray, 0);
        for (int i=0; i<asIsBytes+2; i++){
            byteList.add(byteArray[i]);
        }
        for (int i=asIsBytes+2; i<size; i++){
            decompressEngine.writeBits(value, (int)byteArray[i]);
            value = value==0?1:0;
        }
        //System.out.println("byteList.size = "+size);
        return byteList;
    }
    private int getIntFrom2ByteArray(byte[] byteArray, int startInx) {
        return ((byteArray[startInx] & 0xFF) << 8) |
                ((byteArray[startInx +1] & 0xFF) << 0);
    }
}
