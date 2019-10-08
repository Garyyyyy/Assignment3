import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class EmailGenerator {

    private String sendType;
    private String templateType;
    private String templatePath;
    private String outputPath;
    private List<Customer> customers= new ArrayList<>();

    public EmailGenerator(String sendType, String templateType, String templatePath, String outputPath) {
        this.sendType = sendType;
        this.templateType = templateType;
        this.templatePath = templatePath;
        this.outputPath = outputPath;
    }

    public void generate(String csvFile){
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ",";
        String template = readTemplateToString();
        try{
            // read s\csv file into memory
            br = new BufferedReader(new FileReader(csvFile));
            int count = 0;
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] customerInfo = line.split(csvSplitBy);
                if(count == 0 || customerInfo.length < 12){
                    count++;
                    continue;
                }

                Customer customer = new Customer(customerInfo[0], customerInfo[1], customerInfo[2], customerInfo[3],
                        customerInfo[4], customerInfo[5], customerInfo[6], customerInfo[7]
                        , customerInfo[8], customerInfo[9], customerInfo[10], customerInfo[11]);
                String new_template = template;
                String nt = new_template.replaceAll("\\["," ").replaceAll("\\]", " ");
                System.out.println(nt);
//                new_template.replaceAll("[[vhvj]]", "");
                String finalTemplate = nt.replaceAll("email", customer.getEmail()).replaceAll("first_name", customer.getFirst_name()).replaceAll("last_name", customer.getLast_name())
                .replaceAll("address", customer.getAddress()).replaceAll("city", customer.getCity()).replaceAll("state", customer.getState()).replaceAll("zip"
                        , customer.getZip());
//                System.out.println(finalTemplate);
                writeTemplateToFile(finalTemplate, customer);
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void writeTemplateToFile(String generatedTemplate, Customer customer){
        try{

            BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath  + customer.getFirst_name()+ customer.getLast_name()));
            writer.write(generatedTemplate);
            writer.close();
        }catch(IOException e){

        }
    }

    public String readTemplateToString(){
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines( Paths.get(templatePath), StandardCharsets.UTF_8))
        {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}
