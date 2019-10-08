public class Main {
    public static void main(String[] args) {
        String sendType;
        String templateType;
        String templatePath;
        String csvFilePath;

        if (args.length == 0) {
            System.out.println("There were no commandline arguments passed!");
        }

        sendType = getValidSendType(args);
        templateType = getValidTemplateType(args);
        if(!combinationCheck(sendType, templateType)){
            System.out.println("Error: " + sendType + " provided but " + templateType +" was given.");
            return;
        }
        templatePath = getValidTemplatePath(args);
        csvFilePath = getValidCsvFilePath(args);

        EmailGenerator emailGen = new EmailGenerator(sendType, templateType, templatePath, csvFilePath);
        emailGen.generate();
//        System.out.println(emailGen.readTemplateToString());

    }

    private static String getValidCsvFilePath(String[] args) {
        return args[3];
    }

    private static String getValidSendType(String[] args) {
        if (args[0].equals("--email")) {
             return "--email";
        }else if(args[0].equals("--letter")){
            return "--letter";
        }
        return null;
    }

    private static String getValidTemplateType(String[] args){
        if (args[1].equals("--email-template")) {
            return "--email-template";
        }else if(args[1].equals("--letter-template")){
            return "--letter-template";
        }
        return null;
    }

    private static String getValidTemplatePath(String[] args){
        return args[2];
    }

    private static boolean combinationCheck(String sendType, String templateType){
        if((sendType.equals("--email") && templateType.equals("--email-template")) || (sendType.equals("--letter") && templateType.equals("--letter-template"))){
            return true;
        }
        return false;
    }
}
