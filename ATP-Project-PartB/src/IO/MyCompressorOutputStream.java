package IO;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

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

    private void compressMatrix(byte[] inArray, int startInIndex, List<Byte> outList) {
        int localCounter = 0;
        byte currentByte = 0x00;
        for (int i = startInIndex; i < inArray.length; i++) {
            if (localCounter == 8) {
                localCounter = 0;
                outList.add(currentByte);
                currentByte = 0x00;
            }
            if (inArray[i] == 1) {
                currentByte |= 1 << localCounter;
            }
            localCounter++;
        }
        if (localCounter > 1) {
            outList.add(currentByte);
        }
    }

    private byte[] compress(byte[] byteArray) {
        ArrayList<Byte> byteList = new ArrayList<>();
        int asIsByte = getIntFrom2ByteArray(byteArray, 0);
        for (int i = 0; i < asIsByte + 2; i++) {
            byteList.add(byteArray[i]);
            //System.out.printf("byteArray[%d] = %x\n", i, byteArray[i]);
        }
        compressMatrix(byteArray, asIsByte + 2, byteList);
        byte[] data = new byte[byteList.size()];
        for (int i = 0; i < data.length; i++) {
            data[i] = byteList.get(i);
            //System.out.printf("Data[%d] = %s\n",i, byteToString(data[i]));
        }
        //System.out.println("array after compression : "+data.length);
        return data;
    }

    private int getIntFrom2ByteArray(byte[] byteArray, int startInx) {
        return ((byteArray[startInx] & 0xFF) << 8) |
                ((byteArray[startInx + 1] & 0xFF) << 0);
    }

    private String byteToString(byte b) {
        String s = "";
        for (int i = 7; i >= 0; i--) {
            s += ((b >> i) & 1) == 1 ? "1" : "0";
        }
        return s;
    }
}

