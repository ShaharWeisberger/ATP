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
        int bitValue, count = 0, prev = 0;
        ArrayList<Byte> byteList = new ArrayList<>();
        for (int i = 0; i < byteArray.length; i++) {
            System.out.printf("byteArray[%d] = %x\n",i, byteArray[i]);
            for (int j = 7; j >= 0; j--) {
                bitValue = ((byteArray[i] >> j) & 1) == 1 ? 1 : 0;
                if (bitValue == prev) {
                    count++;
                }
                else{
                    prev = bitValue;
                    addCountToByteList(byteList, count);
                    count = 1;
                }
            }
        }
        if(count != 0) {
            addCountToByteList(byteList, count);
        }
        byte[] data = new byte[byteList.size()];
        for (int i = 0; i < data.length; i++) {
            data[i] = byteList.get(i);
            System.out.printf("Data[%d] = %x\n",i, data[i]);
        }
        return data;
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

