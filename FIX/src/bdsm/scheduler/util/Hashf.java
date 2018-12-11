package bdsm.scheduler.util;

/**
 *
 * @author bdsm
 */

import java.io.*;

/**
 * 
 * @author bdsm
 */
public class Hashf {

    /**
     * 
     * @param s
     * @param s1
     * @param p_password
     * @throws IOException
     */
    public static void Encrypt(String s, String s1, String p_password) throws IOException {
        BufferedReader l_file_in = new BufferedReader(new FileReader(s));
        BufferedWriter l_file_out = new BufferedWriter(new FileWriter(s1));
        String s3;
        while ((s3 = l_file_in.readLine()) != null)  {
            Stretch(s3, p_password, l_file_out);
            l_file_out.newLine();
        }
        l_file_in.close();
        l_file_out.close();
    }

    /**
     * 
     * @param s
     * @param s1
     * @param p_password
     * @throws IOException
     */
    public static void Decrypt(String s, String s1, String p_password) throws IOException {
        BufferedReader l_file_in = new BufferedReader(new FileReader(s));
        BufferedWriter l_file_out = new BufferedWriter(new FileWriter(s1));
        int l_i = 0;
        String s3;
        while ((s3 = l_file_in.readLine()) != null)  {
            l_i = 0;
            for (int j = 0; j < s3.length(); j += 4) {
                String s4 = j + 4 > s3.length() ? s3.substring(j) : s3.substring(j, j + 4);
                l_i = shrink(s4, p_password, l_file_out, l_i);
            }

            l_file_out.newLine();
        }
        l_file_in.close();
        l_file_out.close();
    }

    private static int shrink(String s, String s1, BufferedWriter p_file_out, int p_i) throws IOException, UnsupportedEncodingException {
        byte abyte0[] = s1.getBytes("UTF8");
        byte abyte1[] = s.getBytes("UTF8");
        int j = abyte1.length;
        int l;
        int i1 = l = 0;
        for (int j1 = 0; j1 < j - 1; j1++) {
            int k1 = abyte1[j1] - 59;
            switch ((j1 + 1) % 3) {
            case 1: // '\001'
                i1 = abyte1[j - 1] - 59;
                l = (i1 / 16 & 3) * 64;
                break;

            case 2: // '\002'
                l = (i1 / 4 & 3) * 64;
                break;

            case 0: // '\0'
                l = (i1 & 3) * 64;
                break;
            }
            k1 = (k1 | l) ^ abyte0[p_i % abyte0.length];
            p_file_out.write(k1);
            p_i++;
        }

        return p_i;
    }

    private static void Stretch(String s, String s1, BufferedWriter p_file_out) throws IOException, UnsupportedEncodingException {
        byte abyte0[] = s.getBytes("UTF8");
        byte abyte1[] = s1.getBytes("UTF8");
        int i = 0;
        int k = 0;
        for (int j = 0; j < abyte0.length; j++) {
            int l = abyte0[j] ^ abyte1[i % abyte1.length];
            p_file_out.write((l & 0x3f) + 59);
            switch ((i + 1) % 3) {
            case 1: // '\001'
                k |= (l / 64) * 16;
                break;

            case 2: // '\002'
                k |= (l / 64) * 4;
                break;

            case 0: // '\0'
                k |= l / 64;
                p_file_out.write(k + 59);
                k = 0;
                break;
            }
            i++;
        }

        if (i % 3 != 0)
            p_file_out.write(k + 59);
    }

}
