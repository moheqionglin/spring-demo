import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author wanli.zhou
 * @description
 * @time 2020-01-01 11:17
 */
public class P {
    public static void main(String[] args) throws IOException {
        Map<String, List<JSONObject>> collect = Files.lines(Paths.get("/Users/wanli.zhou/Desktop/Edit14"))
                .map(lin -> {
                    String[] split = lin.split(",");
                    JSONObject o = new JSONObject();
                    o.put("id", split[0]);
                    o.put("name", split[1]);
                    o.put("image", split[2]);
                    o.put("sort", split[3]);
                    o.put("parent_id", split[4]);
                    o.put("product_cnt", new Random().nextInt(100));
                    return o;
                }).collect(Collectors.groupingBy(o -> o.getString("parent_id")))
                ;
        Map<String, List<JSONObject>> pimap = collect.get("0").stream().collect(Collectors.groupingBy(o -> o.getString("id")));
        Map<String, JSONObject> m = new HashMap<>();
        pimap.entrySet().stream().forEach(e -> {
            m.put(e.getKey(), e.getValue().get(0));
        });


        for(String key : collect.keySet()){
            try{
                m.get(key).put("child", collect.get(key).toString());
            }catch (Exception e){
                System.out.println(key);
            }

        }
        for(String k : m.keySet()){
            System.out.println(m.get(k).toJSONString());
        }
    }
}