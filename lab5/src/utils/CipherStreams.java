package utils;

import java.io.FilterReader;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class CipherStreams {

    public static class EncryptionWriter extends FilterWriter {
        private final int key;

        public EncryptionWriter(Writer out, char keyChar) {
            super(out);
            this.key = keyChar;
        }

        @Override
        public void write(int c) throws IOException {
            super.write(c + key);
        }

        @Override
        public void write(String str, int off, int len) throws IOException {
            for (int i = off; i < off + len; i++) {
                write(str.charAt(i));
            }
        }
    }

    public static class DecryptionReader extends FilterReader {
        private final int key;

        public DecryptionReader(Reader in, char keyChar) {
            super(in);
            this.key = keyChar;
        }

        @Override
        public int read() throws IOException {
            int c = super.read();
            if (c == -1) return -1;
            return c - key;
        }

        @Override
        public int read(char[] cbuf, int off, int len) throws IOException {
            int n = super.read(cbuf, off, len);
            if (n == -1) return -1;
            for (int i = off; i < off + n; i++) {
                cbuf[i] = (char) (cbuf[i] - key);
            }
            return n;
        }
    }
}