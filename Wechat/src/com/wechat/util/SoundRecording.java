package com.wechat.util;

import java.io.*;  
import javax.sound.sampled.*;  
  
public class SoundRecording{  
  
    //定义录音格式  
    AudioFormat af = null;  
    //定义目标数据行,可以从中读取音频数据,该 TargetDataLine 接口提供从目标数据行的缓冲区读取所捕获数据的方法。  
    TargetDataLine td = null;  
    //定义源数据行,源数据行是可以写入数据的数据行。它充当其混频器的源。应用程序将音频字节写入源数据行，这样可处理字节缓冲并将它们传递给混频器。  
    SourceDataLine sd = null;  
    //定义字节数组输入输出流  
    ByteArrayInputStream bais = null;  
    ByteArrayOutputStream baos = null;  
    //定义音频输入流  
    AudioInputStream ais = null;  
    //定义停止录音的标志，来控制录音线程的运行  
    Boolean stopflag = false;  
    String user_id;
    boolean boo = true;
    public SoundRecording(String user_id){
    	this.user_id = user_id;
    }
    public static void main(String[] args) {  
          
//    	//调用录音的方法  
//        capture();  
//    	//调用停止录音的方法       
//        stop();  
//    	//调用播放录音的方法  
//        play();  
//    	//调用保存录音的方法  
//        save();  
        //创造一个实例  
//        Test mr = new Test();
    	SoundRecording mr = new SoundRecording("123");
        try {
			mr.play("F:\\语音文件\\1527863150667.mp3");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }  
    //开始录音  
    public void capture()  
    {  
        try {  
            //af为AudioFormat也就是音频格式  
            af = getAudioFormat();  
            DataLine.Info info = new DataLine.Info(TargetDataLine.class,af);  
            td = (TargetDataLine)(AudioSystem.getLine(info));  
            //打开具有指定格式的行，这样可使行获得所有所需的系统资源并变得可操作。  
//            if(boo){
//            	td.open(af);
//            	boo = false;
//            }
            td.open(af);
             
            //允许某一数据行执行数据 I/O  
            td.start();  
              
            //创建播放录音的线程  
            Record record = new Record();  
            Thread t1 = new Thread(record);  
            t1.start();  
              
        } catch (LineUnavailableException ex) {  
            ex.printStackTrace();  
            return;  
        }  
    }  
    //停止录音  
    public void stop()  
    {  
        stopflag = true;              
    }  
    //播放录音  
    public void play()  
    {  
        //将baos中的数据转换为字节数据  
        byte audioData[] = baos.toByteArray();  
        //转换为输入流  
        bais = new ByteArrayInputStream(audioData);  
//        bais = new ByteArrayInputStream(); 
        af = getAudioFormat();  
        ais = new AudioInputStream(bais, af, audioData.length/af.getFrameSize());  
          
        try {  
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, af);  
            sd = (SourceDataLine) AudioSystem.getLine(dataLineInfo);  
            sd.open(af);  
            sd.start();  
            //创建播放进程  
            Play py = new Play();  
            Thread t2 = new Thread(py);  
            t2.start();             
        } catch (Exception e) {  
            e.printStackTrace();  
        }finally{  
            try {  
                //关闭流  
                if(ais != null)  
                {  
                    ais.close();  
                }  
                if(bais != null)  
                {  
                    bais.close();  
                }  
                if(baos != null)  
                {  
                    baos.close();  
                }  
                  
            } catch (Exception e) {       
                e.printStackTrace();  
            }  
        }  
          
    }  
    //播放录音  
    public void play(String path) throws IOException  
    {  
    	Runtime.getRuntime()
		.exec(path);
    	
//        //将baos中的数据转换为字节数据  
//        byte audioData[] = getContent(path);  
//        //转换为输入流  
//        bais = new ByteArrayInputStream(audioData);  
////        bais = new ByteArrayInputStream(); 
//        af = getAudioFormat();  
//        ais = new AudioInputStream(bais, af, audioData.length/af.getFrameSize());  
//          
//        try {  
//            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, af);  
//            sd = (SourceDataLine) AudioSystem.getLine(dataLineInfo);  
//            sd.open(af);  
//            sd.start();  
//            //创建播放进程  
//            Play py = new Play();  
//            Thread t2 = new Thread(py);  
//            t2.start();             
//        } catch (Exception e) {  
//            e.printStackTrace();  
//        }finally{  
//            try {  
//                //关闭流  
//                if(ais != null)  
//                {  
//                    ais.close();  
//                }  
//                if(bais != null)  
//                {  
//                    bais.close();  
//                }  
//                if(baos != null)  
//                {  
//                    baos.close();  
//                }  
//                  
//            } catch (Exception e) {       
//                e.printStackTrace();  
//            }  
//        }  
          
    }  
//    @SuppressWarnings("resource")
//	public byte[] getContent(String filePath) throws IOException {  
//        File file = new File(filePath);  
//        long fileSize = file.length();  
//        if (fileSize > Integer.MAX_VALUE) {  
//            System.out.println("file too big...");  
//            return null;  
//        }  
//        FileInputStream fi = new FileInputStream(file);  
//        byte[] buffer = new byte[(int) fileSize];  
//        int offset = 0;  
//        int numRead = 0;  
//        while (offset < buffer.length  
//        && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {  
//            offset += numRead;  
//        }  
//        // 确保所有数据均被读取  
//        if (offset != buffer.length) {  
//        throw new IOException("Could not completely read file "  
//                    + file.getName());
//        }  
//        fi.close();  
//        return buffer;  
//    }  
    //保存录音  
    public String save()  
    {  
         //取得录音输入流  
        af = getAudioFormat();  
  
        byte audioData[] = baos.toByteArray();  
        bais = new ByteArrayInputStream(audioData);  
        ais = new AudioInputStream(bais,af, audioData.length / af.getFrameSize());  
        //定义最终保存的文件名  
        File file = null;  
        String path = "wechat\\"+user_id+"\\"+"voice";
        //写入文件  
        try {     
            //以当前的时间命名录音的名字  
            //将录音的文件存放到F盘下语音文件夹下  
            File filePath = new File(path);  
            if(!filePath.exists())  
            {//如果文件不存在，则创建该目录  
                filePath.mkdir();  
            }  
            path = path+"\\"+System.currentTimeMillis()+".mp3";
            file = new File(path);        
            AudioSystem.write(ais, AudioFileFormat.Type.WAVE, file);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }finally{  
            //关闭流  
            try {  
                  
                if(bais != null)  
                {  
                    bais.close();  
                }   
                if(ais != null)  
                {  
                    ais.close();          
                }  
                td.close();
            } catch (Exception e) {  
                e.printStackTrace();  
            }         
        }  
        return path;
    }  
    //设置AudioFormat的参数  
    public AudioFormat getAudioFormat()   
    {  
        //下面注释部分是另外一种音频格式，两者都可以  
        AudioFormat.Encoding encoding = AudioFormat.Encoding.  
        PCM_SIGNED ;  
        float rate = 8000f;  
        int sampleSize = 16;  
        String signedString = "signed";  
        boolean bigEndian = true;  
        int channels = 1;  
        return new AudioFormat(encoding, rate, sampleSize, channels,  
                (sampleSize / 8) * channels, rate, bigEndian);  
//      //采样率是每秒播放和录制的样本数  
//      float sampleRate = 16000.0F;  
//      // 采样率8000,11025,16000,22050,44100  
//      //sampleSizeInBits表示每个具有此格式的声音样本中的位数  
//      int sampleSizeInBits = 16;  
//      // 8,16  
//      int channels = 1;  
//      // 单声道为1，立体声为2  
//      boolean signed = true;  
//      // true,false  
//      boolean bigEndian = true;  
//      // true,false  
//      return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed,bigEndian);  
    }  
    //录音类，因为要用到MyRecord类中的变量，所以将其做成内部类  
    class Record implements Runnable  
    {  
        //定义存放录音的字节数组,作为缓冲区  
        byte bts[] = new byte[10000];  
        //将字节数组包装到流里，最终存入到baos中  
        //重写run函数  
        public void run() {   
            baos = new ByteArrayOutputStream();       
            try {  
                System.out.println("ok3");  
                stopflag = false;  
                while(stopflag != true)  
                {  
                    //当停止录音没按下时，该线程一直执行   
                    //从数据行的输入缓冲区读取音频数据。  
                    //要读取bts.length长度的字节,cnt 是实际读取的字节数  
                    int cnt = td.read(bts, 0, bts.length);  
                    if(cnt > 0)  
                    {  
                        baos.write(bts, 0, cnt);  
                    }  
                }  
            } catch (Exception e) {  
                e.printStackTrace();  
            }finally{  
                try {  
                    //关闭打开的字节数组流  
                    if(baos != null)  
                    {  
                        baos.close();  
                    }     
                } catch (IOException e) {  
                    e.printStackTrace();  
                }finally{  
                    td.drain();  
                    td.close();  
                }  
            }  
        }  
          
    }  
    //播放类,同样也做成内部类  
    class Play implements Runnable  
    {  
        //播放baos中的数据即可  
        public void run() {  
            byte bts[] = new byte[10000];  
            try {  
                int cnt;  
                //读取数据到缓存数据  
                while ((cnt = ais.read(bts, 0, bts.length)) != -1)   
                {  
                    if (cnt > 0)   
                    {  
                        //写入缓存数据  
                        //将音频数据写入到混频器  
                        sd.write(bts, 0, cnt);  
                    }  
                }  
                 
            } catch (Exception e) {  
                e.printStackTrace();  
            }finally{  
                 sd.drain();  
                 sd.close();  
            }  
              
              
        }         
    }     
  //播放录音
  	public static void play1(String path) throws UnsupportedAudioFileException, IOException, LineUnavailableException{

          // 获取音频输入流
          AudioInputStream audioInputStream = AudioSystem
                  .getAudioInputStream(new File(path));
          // 获取音频编码对象
          AudioFormat audioFormat = audioInputStream.getFormat();
    
          // 设置数据输入
          DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class,
                  audioFormat, AudioSystem.NOT_SPECIFIED);
          SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem
                  .getLine(dataLineInfo);
          sourceDataLine.open(audioFormat);
          sourceDataLine.start();
    
          /*
           * 从输入流中读取数据发送到混音器
           */
          int count;
          byte tempBuffer[] = new byte[1024];
          while ((count = audioInputStream.read(tempBuffer, 0, tempBuffer.length)) != -1) {
              if (count > 0) {
                  sourceDataLine.write(tempBuffer, 0, count);
              }
          }
    
          // 清空数据缓冲,并关闭输入
          sourceDataLine.drain();
          sourceDataLine.close();
  	}
}

