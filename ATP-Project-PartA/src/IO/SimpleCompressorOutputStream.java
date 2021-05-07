package IO;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class SimpleCompressorOutputStream extends OutputStream {

    private OutputStream out;

    public SimpleCompressorOutputStream(OutputStream out) {
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

    private byte[] compress(byte[] byteArray) {
        int value, count = 0, prev = 0;
        ArrayList<Byte> byteList = new ArrayList<>();
        int asIsByte = getIntFrom2ByteArray(byteArray, 0);
        for (int i=0; i<asIsByte+2; i++){
            byteList.add(byteArray[i]);
            //System.out.printf("byteArray[%d] = %x\n", i, byteArray[i]);
        }
        for (int i = asIsByte+2; i < byteArray.length; i++) {
            //System.out.printf("byteArray[%d] = %x\n", i, byteArray[i]);
            value = byteArray[i];
            if (value == prev) {
                count++;
            } else {
                prev = value;
                addCountToByteList(byteList, count);
                count = 1;
            }
        }
        if(count != 0) {
            addCountToByteList(byteList, count);
        }
        byte[] data = new byte[byteList.size()];
        for (int i = 0; i < data.length; i++) {
            data[i] = byteList.get(i);
            //System.out.printf("Data[%d] = %x\n",i, data[i]);
        }
        System.out.println("array after compression : "+data.length);
        return data;
    }

    private int getIntFrom2ByteArray(byte[] byteArray, int startInx) {
        return ((byteArray[startInx] & 0xFF) << 8) |
                ((byteArray[startInx +1] & 0xFF) << 0);
    }

    private void addCountToByteList(ArrayList<Byte> byteList, int count) {
        while (count >= 256) {
            byteList.add((byte) 255);
            byteList.add((byte) 0);
            count -= 255;
        }
        byteList.add((byte) count);
    }
}

