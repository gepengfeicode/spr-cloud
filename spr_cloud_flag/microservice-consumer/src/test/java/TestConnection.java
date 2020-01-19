import ch.qos.logback.classic.spi.ILoggingEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestConnection {
    /**
     * 先加上此方法  因测试环境调用地址需要                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        代理
     * @return
     */
    public static String payMenthttpConnection(String reqStr,String path) throws Exception{
        StringBuffer stringBuffer = new StringBuffer();
        HttpURLConnection httpURLConnection = null;
        OutputStreamWriter printWriter=null;
        OutputStream outputStream = null;
        try {
            BufferedReader reader = null;
            URL url = new URL(path);
//           Proxy proxy = new Proxy(Proxy.Type.HTTP,new InetSocketAddress("proxy.epicc.intra",8888));
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestProperty("content-type","text/xml,charset=UTF-8");
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            outputStream=httpURLConnection.getOutputStream();
            printWriter =new OutputStreamWriter(outputStream,"GBK");//填写数据
            printWriter.write(reqStr);
            printWriter.flush();//不等缓存区数据满后在进行推送 直接推送数据
            printWriter.close();
            if(200 != httpURLConnection.getResponseCode()){
//                logger.info(LogConstantInfo.INFO_FOR_INSURE_FOUR, "链接异常,链接地址>>>>>>>>>>>>{},异常信息》》》》》》》》{},链接状态码", httpURLConnection.getResponseCode(), path, httpURLConnection.getResponseMessage(), "", ILoggingEvent.LOGER_TYPE_UUID);
                throw new ConnectException("调用接口异常,调用地址>>>>"+path);
            }
            reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"GBK"));
            String str = null;
            while(null != (str = reader.readLine())){
                stringBuffer.append(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
//            logger.warn(LogConstantInfo.HTTP_REQUEST,"调用异常",errorMsg,"", ILoggingEvent.LOGER_TYPE_UUID);
        }finally {
            if(null != httpURLConnection){
                httpURLConnection.disconnect();
            }
        }
        return String.valueOf(stringBuffer);
    }

    public static void main(String[] args) throws Exception {
        String reqStr = "<?xml version=\"1.0\" encoding=\"GBK\"?><Packet type=\"REQUEST\" version=\"1.0\">\n" +
                "  <Head>\n" +
                "    <RequestType>Z05</RequestType>\n" +
                "    <UserCode>PICC</UserCode>\n" +
                "    <PassWord>123456</PassWord>\n" +
                "  </Head>\n" +
                "  <Body>\n" +
                "    <MessageInfo>\n" +
                "      <SMSAuthenticationNo>01173c29-1fcb-424f-aef1-7dcab01ea1d5</SMSAuthenticationNo>\n" +
                "      <VerificationNo>2311</VerificationNo>\n" +
                "    </MessageInfo>\n" +
                "  </Body>\n" +
                "</Packet>";
        String url = "http://88.32.0.144:90/sinoinpcService/interfaceServer";
        System.out.println(payMenthttpConnection(reqStr,url));;
    }
}
