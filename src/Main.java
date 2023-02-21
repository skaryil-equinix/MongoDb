import org.json.JSONArray;
import org.json.JSONObject;


public class Main {
    public static void main(String[] args) {

        MongoDb db = new MongoDb("athlete"); // create a DB

        //Creating collections
        db.createCollection("footballer");
        db.createCollection("cricketer");


        JSONObject cricketerObject1 = new JSONObject();
        cricketerObject1.put("name", "sherinkk");
        cricketerObject1.put("jersey_number", "23");

        JSONObject cricketerObject2 = new JSONObject();
        cricketerObject2.put("name", "ab de villers");
        cricketerObject2.put("age", "40");

        // Adding Documents to collections
        db.insert("cricketer",cricketerObject1);
        db.insert("cricketer",cricketerObject2);

        // reading one document based on the Id
        System.out.println(db.findById("cricketer","1"));

        // Reading all the Documents in a collection
        JSONArray documents = db.findAll("cricketer");
        if(documents!=null){
            for (int i = 0; i < documents.length(); i++) {
                JSONObject tempObj = documents.getJSONObject(i);
                System.out.println(tempObj);
            }
        }

        // drop a collection based on the name
        db.dropAll("cricketer");
    }
}