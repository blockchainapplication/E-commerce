package noobchain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

//	用于从csv文件中读取coupon列表
public class ReadData {
	public static CouponData[] GetData(String file_name) throws IOException, ParseException {
		BufferedReader read_data = new BufferedReader(new FileReader(file_name)); 
		read_data.readLine(); 
		String line = null;    
        CouponData[] data = new CouponData[100];
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        int n = 0;
        while((line=read_data.readLine())!=null){    
            String Create[] = line.split(",");
            data[n] = new CouponData();
            data[n].occur_time = dateFormat.parse(Create[0]);
            data[n].ID = Create[1];
            data[n].money = Integer.parseInt(Create[2]);
            data[n].expire_time = dateFormat.parse(Create[3]);
            data[n].user = Create[4];
            data[n].status = Create[5];
            n++;
        }
        read_data.close();
		return data;
	}
}

