package IO;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
        byte[] tempArray = new byte[b.length];
        int size = in.read(tempArray);
        ArrayList<Byte> byteList = decompress(tempArray, size);
        for (int i = 0; i < byteList.size(); i++) {
            b[i] = byteList.get(i);
        }
        return byteList.size();
    }

    private ArrayList<Byte> decompress(byte[] byteArray, int size) {
        ArrayList<Byte> byteList = new ArrayList<>();
        int asIsBytes = getIntFrom2ByteArray(byteArray, 0);
        for (int i = 0; i < asIsBytes + 2; i++) {
            byteList.add(byteArray[i]);
        }
        decompressMatrix(byteArray, size,asIsBytes + 2, byteList);
        //System.out.println("byteList.size = " + size);
        return byteList;
    }

    private int getIntFrom2ByteArray(byte[] byteArray, int startInx) {
        return ((byteArray[startInx] & 0xFF) << 8) |
                ((byteArray[startInx +1] & 0xFF) << 0);
    }

    private void decompressMatrix(byte[] inArray, int size,int startInIndex, List<Byte> outList) {
        for (int i = startInIndex; i < size; i++) {
            for (int j = 0; j < 8; j++) {
                outList.add(((inArray[i] >> j) & 1) == 1 ? (byte) 1 : (byte) 0);
            }
        }
    }
}
