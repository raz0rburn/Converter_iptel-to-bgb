
package com.conv;
import java.io.*;
import java.util.*;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by ivanovkn on 19.05.2015.
 */
public  class FileWorker {
    private List<String> List1 = new ArrayList<String>();
    private String filepath = "";
    private List<String[]> ListMas = new ArrayList<String[]>();
    private Map<String, String> dayHourMap = new HashMap();
    private String atsPath = "";
    private String srcLogPath = "";
    private File[] srcLogList;
    private List<String> fileList;

    public FileWorker(String str,String str2) {
        srcLogPath = str;
        atsPath = str2;
        this.init();
        //this.filepath = str;
        //this.Read(str);
        this.ReadFilesInDir();
        this.Parser();

        this.Process();
        // this.Print_SourceData();
        this.WriteToFile();
        fileList = new ArrayList<String>();
        this.zipIt();

    }

    private void init() {
        File srcDirectory = new File(this.srcLogPath);
        if (srcDirectory.isDirectory()) {
            this.srcLogList = srcDirectory.listFiles();
            if (this.srcLogList == null || this.srcLogList.length <= 0) {
                System.err.println("err!");
                System.exit(1);
            }
        } else {
            System.err.println(this.srcLogPath + " - err2!");
            System.exit(1);
        }

    }

    private void ReadFilesInDir() {
        long timeStart = 0L;
        long timeFinish = 0L;
        long timeProcess = 0L;
        long totalProcessTime = 0L;
        File[] var12 = this.srcLogList;
        int var11 = this.srcLogList.length;

        for (int var10 = 0; var10 < var11; ++var10) {
            File f = var12[var10];
            if (!f.isDirectory() && !f.getName().startsWith(".")) {

                String filename = srcLogPath + "/" + f.getName();

                System.out.println(filename);

                Read(filename);


            }
        }
        //Read("C://DSSL/Logs/cdr1.IPTEL-1.05_15_2015_08_57_34.200");
        // Read("C://DSSL/Logs/cdr1.IPTEL-1.05_19_2015_17_35_11.907");
        // Read("C://DSSL/Logs/cdr1.IPTEL-1.05_21_2015_05_33_23.564");
        //Read("C://DSSL/Logs/cdr1.IPTEL-1.05_15_2015_13_45_04.967");
        //Read("C://DSSL/Logs/cdr1.IPTEL-1.05_27_2015_00_37_05.496");
        // Read("C://DSSL/Logs/cdr1.IPTEL-1.05_28_2015_04_41_03.039");
        // Read("C://DSSL/Logs/cdr1.IPTEL-1.05_13_2015_07_33_20.589");
    }

    private void Read(String filename) {
        FileReader myFile = null;
        BufferedReader buff = null;

        try {

            myFile = new FileReader(filename);

            buff = new BufferedReader(myFile);

            while (true) {

                // считываетс€ строка из файла scores.txt

                String line = buff.readLine();

                // проверка достижени€ конца файла

                if (line == null)
                    break;
                //System.out.println(line);

                List1.add(line);


            } // конец цикла while

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                buff.close();

                myFile.close();

            } catch (Exception e) {

                System.out.println("ERROR:" + e);

            }

            System.out.println("End reading.");
        }


    }

    private void Parser() {

        for (String temp : List1) {
            //System.out.println(temp);
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = null;

            String[] array = {"", "", "", "", ""};
            try {
                array = temp.split("\\,");
            } catch (Exception e) {

                System.out.println("ERROR:" + e);

            }
            String[] temp_array2 = {"", "", "", "", ""};
            if (array != null) {
                if (array.length == 35) {

                    for (int i = 0; i < array.length; i++) {

                        // System.out.println(i);
                        //System.out.println(array[i]);
                        if (i == 7) {

                            temp_array2[0] = array[7].substring(1, (array[7].length() - 1));


                            String[] strArr = temp_array2[0].split(" ");

                            temp_array2[0] = "";
                            for (int j = 0; j < strArr.length; j++) {
                                if (j != 1)
                                    temp_array2[0] = temp_array2[0] + (strArr[j] + " ");
                            }
                            if (temp_array2[0].startsWith(".")) temp_array2[0] = temp_array2[0].substring(1);
                            //dateFormat = new SimpleDateFormat("HH:mm:ss.S z EEE MMM d yyyy");
                            // System.out.println("Constructor 2: " + dateFormat.format( temp_array2[0] ) );

                            //Date date = new SimpleDateFormat("HH:mm:ss.S z E MMM d yyyy").parse(temp_array2[0]);


                        }
                        if (i == 10) {

                            temp_array2[1] = array[9].substring(1, (array[9].length() - 1));

                            String[] strArr = temp_array2[1].split(" ");
                            temp_array2[1] = "";
                            for (int j = 0; j < strArr.length; j++) {
                                if (j != 1)
                                    temp_array2[1] = temp_array2[1] + (strArr[j] + " ");
                            }
                            if (temp_array2[1].startsWith(".")) temp_array2[1] = temp_array2[1].substring(1);
                        }
                        if (i == 11) {

                            temp_array2[2] = array[10].substring(1, (array[10].length() - 1));

                            String[] strArr = temp_array2[2].split(" ");
                            temp_array2[2] = "";
                            for (int j = 0; j < strArr.length; j++) {
                                if (j != 1)
                                    temp_array2[2] = temp_array2[2] + (strArr[j] + " ");
                            }
                            if (temp_array2[2].startsWith(".")) temp_array2[2] = temp_array2[2].substring(1);
                        }
                        if (i == 21) temp_array2[3] = array[21].substring(1, (array[21].length() - 1));
                        if (i == 22) temp_array2[4] = array[22].substring(1, (array[22].length() - 1));
                    }

                    ListMas.add(temp_array2);

                    //System.out.println();
                }
            }

        }
    }

    private void Process() {
        int i = 0;
        String pattern = "HH:mm:ss.S EEE MMM d y";
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.ENGLISH);
        while (i < ListMas.size()) {
            String[] temp_array2 = {"", "", "", "", ""};
            temp_array2 = ListMas.get(i);
            //System.out.println(temp_array2[0]);
            //System.out.println(temp_array2[1]);
            //System.out.println(temp_array2[2]);
            //System.out.println(temp_array2[3]);
            //System.out.println(temp_array2[4]);
            i++;


            try {
                String numberA = temp_array2[3];
                String numberAB = temp_array2[3];
                String numberB = temp_array2[4];
                String numberBB = temp_array2[4];
                String dateString = null;
                SimpleDateFormat sdfr = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                Date date1 = format.parse(temp_array2[0]);
                Calendar cal1 = Calendar.getInstance();
                Date date2 = format.parse(temp_array2[1]);
                Calendar cal2 = Calendar.getInstance();
                Date date3 = format.parse(temp_array2[2]);
                Calendar cal3 = Calendar.getInstance();
                cal1.setTime(date1);
                cal2.setTime(date2);
                cal3.setTime(date3);
                int year1 = cal1.get(Calendar.YEAR);
                int month1 = cal1.get(Calendar.MONTH);
                int day1 = cal1.get(Calendar.DAY_OF_MONTH);
                int hour1 = cal1.get(Calendar.HOUR_OF_DAY);
                int year2 = cal2.get(Calendar.YEAR);
                int month2 = cal2.get(Calendar.MONTH);
                int day2 = cal2.get(Calendar.DAY_OF_MONTH);
                int hour2 = cal2.get(Calendar.HOUR_OF_DAY);
                int year3 = cal3.get(Calendar.YEAR);
                int month3 = cal3.get(Calendar.MONTH);
                int day3 = cal3.get(Calendar.DAY_OF_MONTH);
                int hour3 = cal3.get(Calendar.HOUR_OF_DAY);
                long diff = cal3.getTimeInMillis() - cal2.getTimeInMillis();
                //dayHourMap.put(temp_array2[0]+temp_array2[1]+temp_array2[2]+temp_array2[3]+" "+temp_array2[4],Integer.toString(year)+"_"+Integer.toString(month)+"_"+Integer.toString(day)+"_"+Integer.toString(hour));
                ///////////////////////////BITEL////////////////////////////////////////
                //8 4116 622180
                if (numberAB.length() == 10) {
                    numberAB = "7" + numberAB;
                }
                // «амена внешнего номера на внутренний
                if (numberA.length() == 6 && numberA.charAt(0) == '8' || numberA.length() == 5 && (numberA.charAt(0) == '5' || numberA.charAt(0) == '6' || numberA.charAt(0) == '7') || (numberA.length() == 4 && numberA.charAt(0) == '4')) {
                    numberAB = "74112" + numberA;
                }

                // ƒоводим внутренние номера до стандарта
                if (numberBB.length() == 6 || numberBB.length() == 5) {
                    numberBB = "74112" + numberBB;
                }// у сотовых сносим 53, если есть.
                else if (numberBB.length() == 13 && numberBB.startsWith("755")) {
                    numberBB = "7" + numberBB.substring(3);
                }
                // —носим код выхода на международный уровень.
                if (numberBB.startsWith("810")) {
                    numberBB = numberBB.substring(3);
                } else if (numberBB.startsWith("0810")) {
                    numberBB = numberBB.substring(4);
                } else if (numberBB.startsWith("8")) {
                    numberBB = "7" + numberBB.substring(1);
                }

                ///////////////////////////BITEL////////////////////////////////////////
                ////////////////////////////////////////////////////////////


                String key = sdfr.format(date1) + "\t" + Long.toString(diff) + "\t" + numberA + "\t" + numberAB + "\t" + numberB + "\t" + numberBB + "\t" + "0" + "\t" + "0";
                String value = Integer.toString(year1) + "_" + Integer.toString(month1) + "_" + Integer.toString(day1) + "_" + Integer.toString(hour1);
                //System.out.println(dayHourMap.get(temp_array2[0] + temp_array2[1] + temp_array2[2] + temp_array2[3] + temp_array2[4]));
                dayHourMap.put(key, value);

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

    }

    private void Print_SourceData() {
        int i = 0;
        while (i < ListMas.size()) {
            String[] temp_array2 = {"", "", "", "", ""};
            temp_array2 = ListMas.get(i);
            System.out.println(temp_array2[0]);
            System.out.println(temp_array2[1]);
            System.out.println(temp_array2[2]);
            System.out.println(temp_array2[3]);
            System.out.println(temp_array2[4]);
            i++;

        }


    }

    private void WriteToFile() {
        int[] dateparts = {0, 0, 0, 0};
        Set set = dayHourMap.entrySet();
        Iterator i = set.iterator();
        while (i.hasNext()) {

            Map.Entry me = (Map.Entry) i.next();

            //System.out.println(me.getKey() + " : " + me.getValue());


            String key = (String) me.getValue();
            String[] dateParts = key.split("_");


            String str = (String) me.getKey();




         /*  File zipFile = new File(destLogDir, dateParts[2] + "_" + dateParts[3] + ".zip");

            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
            zos.putNextEntry(new ZipEntry(dateParts[2] + "_" + dateParts[3]));


            String str = (String) me.getKey();
            zos.write(str.getBytes());


            zos.closeEntry();
            zos.flush();
            zos.close();*/

            // запись по символам
            //writer.append('\n');
            //writer.append('E');
            FileWriter writeFile = null;
            try {
                File destLogDir = new File(this.atsPath + dateParts[0] + "/" + dateParts[1]);
                if (!destLogDir.exists()) {
                    destLogDir.mkdirs();
                    destLogDir.createNewFile();
                }

                String fileName =this.atsPath + dateParts[0] + "/" + dateParts[1] + "/" + dateParts[2] + "_" + dateParts[3];
                System.out.println(me.getKey() + " : " + me.getValue());
                    File logFile = new File(this.atsPath + dateParts[0] + "/" + dateParts[1] + "/" + dateParts[2] + "_" + dateParts[3]);
                    writeFile = new FileWriter(logFile,true);
                    writeFile.write(str+"\n");
                    // существует


            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (writeFile != null) {
                    try {
                        writeFile.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


        }
    }
    public void generateFileList(File node){
        System.out.println(node.getAbsoluteFile().toString());
        //add file only
        if(node.isFile()){

            fileList.add(node.getAbsoluteFile().toString());
        }

        if(node.isDirectory()){
            String[] subNote = node.list();
            for(String filename : subNote){
                generateFileList(new File(node, filename));
            }
        }

    }
    private String generateZipEntry(String file){
        return file.substring(atsPath.length()-1, file.length());
    }
    private void zipIt(){

        byte[] buffer = new byte[1024];
            File node = new File(atsPath);
                    generateFileList(node);

        try{



            for(String file : this.fileList){

                System.out.println("File Added : " + file);
                ZipEntry ze= new ZipEntry(file.substring(atsPath.length()+6, file.length()));
                FileOutputStream fos = new FileOutputStream(file+".zip");
                ZipOutputStream zos = new ZipOutputStream(fos);

                //System.out.println("Output to Zip : " + zipFile);
                zos.putNextEntry(ze);

                //FileInputStream in = new FileInputStream(atsPath+ File.separator +file.substring(atsPath.length()-1, file.length()));
                FileInputStream in =
                        new FileInputStream(file);
                int len;
                while ((len = in.read(buffer)) >= 0) {
                    zos.write(buffer, 0, len);
                }

                in.close();
                //zos.closeEntry();
                //remember close it
                //zos.flush();
                zos.close();
            }



            System.out.println("Done");
        }catch(IOException ex){
            ex.printStackTrace();
        }


    }


  public  static void main(String[] args)

    {
        if( args.length ==2 && args[0] != null && args[1] != null) {
            FileWorker converter = new FileWorker(args[0], args[1]);}

      // FileWorker converter = new FileWorker("C://DSSL/Logs", "C://DSSL/Converted/");


    }
}
