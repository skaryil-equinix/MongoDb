import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * MongoDb class simulates a MongoDb database
 * Here a file is created for each Collection
 */
public class MongoDb {
    private String dbName;

    private ArrayList<String> collectionsList;

    //to keep track of id of the Documents
    private static long __id__ = 0;

    // parametrised contructor is used, which takes Collection name as the input
    public MongoDb(String dbName){
        this.dbName = dbName;
    }

    /**
     * the method displays names of all the Collections in the current DB
     */
    public void displayAllCollections(){
        for (String collection: collectionsList) {
            System.out.println(collection);
        }
    }

    /**
     * the method creates a json file
     * One json file corresponds to one Collection
     */
    public boolean createCollection(String collectionName){

        File file = new File(collectionName+".json");

        try {
            boolean val = file.createNewFile();
            if (val) {
                collectionsList.add(collectionName);
                return true;
            }
        }
        catch(Exception e) {
            e.getStackTrace();
        }
        return false;
    }

    /**
     * The method returns an array of all the Documents inside a Collection
     */
    public JSONArray findAll(String collectionName){
        try (FileReader reader = new FileReader(collectionName+".json")) {
            JSONTokener data = new JSONTokener(reader);
            JSONArray js = new JSONArray(data);
            return js;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * findById returns details of the specific Document, whose id is mentioned in the arguments
     */
    public JSONObject findById(String collectionName, String _id){

        JSONArray collectionData = findAll(collectionName);
        if(collectionData!=null){
            for (int i = 0; i < collectionData.length(); i++) {
                JSONObject temp = collectionData.getJSONObject(i);

                if(temp.get("_id").equals(_id)){
                    return temp;
                }
            }
        }
        return null;
    }

    /**
     * insert method is used to insert a document into the specifed collectionName
     */
    public String insert(String collectionName,JSONObject data){
        try{
            JSONArray collectionData = findAll(collectionName);
            FileWriter Writer = new FileWriter(collectionName+".json");

            if(collectionData==null){
                collectionData = new JSONArray();
            }

            __id__++; // unique id for each document
            String id = Long.toString(__id__);
            data.put("_id",id);
            collectionData.put((data));
            Writer.write(collectionData.toString());
            Writer.close();

            return id;
        }
        catch (Exception e){
            e.getStackTrace();
        }
        return "Insertion of Document Failed";
    }

    /**
     * DropAll removes all the collections in the Database
     */
    public boolean dropAll(String collectionName){
        try{
            File file = new File(collectionName+".json");
            boolean res = file.delete();
            return res;
        }
        catch (Exception e){
            e.getStackTrace();
        }
        return false;
    }
}