package com.maiqu.evaluatorPlatform;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@SpringBootTest
@RunWith(SpringRunner.class)
class UserCenterBackendApplicationTests {

    @Test
    void testApi() throws UnsupportedEncodingException {
        String url  = "YnppYmJERWlycGZ1TFVtYfDuDqC1G4JLH7oDo4grkBbo54VK2HWyKetQWjgyq5p8ZpYCKMy7eeeNaBa5bJrc372CTnerErK30M2%2BFon%2F65n2Bif1wUJDWcNjDWwBzR6KjzcbxBR3u87%2FDjZRzdULPZAIbTvZLWPyvAXGc7WI1Sf2831uDoPS4a3CsaXvBkKy4J44UAgGU%2B6ETeSa8jnZR1BHXdPkkZxLyuL%2F%2BEkzne44l1E5eEJWqEVj1aQ2Zq%2FYm1cB4qoJ8otRXVrb230DaU7S5M%2B5WHFbReMk2x5BX2%2BDpJdVidGylGROlkeZHErU%2B21LabAVnWBlPjj6ov1SCFPnkwkb0S0m8mEADJbWv1Wgd1q3oPa5%2BzJV4XBrFHfzCbQpEQeYdG91QIx6vKakewxBIgNpb8tb2aVpTKHs72U%3D";
        String decode = URLDecoder.decode(url, "UTF-8");
        System.out.println(decode);
    }

    @Test
    void testExcel() {
        String answerTemp = "[1,2,3]";
        String answer = answerTemp.substring(1,answerTemp.length()-1);
        System.out.println(answer);
        String[] buff = answer.split(",");
        System.out.println(buff);
        String result = "";
        for(int i=0;i<buff.length;i++) {
            result+=buff[i];
        }
        System.out.println(result);
        String res = result.substring(1);
        System.out.println(res);
        char ch[] = res.toCharArray();
        System.out.println(ch);
    }




}
