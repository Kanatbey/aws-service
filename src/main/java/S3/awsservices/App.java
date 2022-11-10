package S3.awsservices;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.nio.file.Paths;

public class App {
    public static void main(String[] args){

        Region region = Region.EU_CENTRAL_1;

        S3Client s3 = S3Client.builder().region(region).build();

        String bucketName = "helloit"; //"bucket" + System.currentTimeMillis();

        String key = "JPG/_MG_5434.JPG"; // key = fileName

        createNewBucket(s3, bucketName, region);

        System.out.println("Uploading object...");

        PutObjectRequest por = PutObjectRequest.builder()
                        .bucket("helloit")
                                .key(key)
                                        .build();

        s3.putObject(por, Paths.get("C:\\Users\\anon\\IdeaProjects\\aws-services\\aws-services\\images\\_MG_5434.JPG"));

        System.out.println("Upload complete");
        System.out.println("%n");
//==============================================================================================

        cleanUp(s3,bucketName,key);//clenUp methodu buckettin ichindegi filedy ochurot

        deleteBucket(s3,bucketName);//buckettin ozun ochurot


    }

    static void createNewBucket(S3Client s3Client, String newBucketName, Region region){
        try {
            s3Client.createBucket(builder -> builder.bucket(newBucketName)
                    .createBucketConfiguration(cbcb -> cbcb.locationConstraint(region.id()))); // cbcb -> CreateBucketConfigurationBuilder

            s3Client.waiter().waitUntilBucketExists(hbr -> hbr.bucket(newBucketName).build()); // hbr = HeadBucketRequest
            System.out.println("Creating bucket: " + newBucketName + " ...");


            System.out.println(newBucketName + " is ready.");
            System.out.printf("%n");
        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

    static void cleanUp(S3Client s3Client, String bucketName, String keyName){

        System.out.println("Cleaning up...");
        try {
            System.out.println("Deleting object: " + keyName);
            s3Client.deleteObject(dor -> dor.bucket(bucketName).key(keyName).build()); // dor =  DeleteObjectRequest

            System.out.println(keyName + " has been deleted.");

        }catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }

    }

    static void deleteBucket(S3Client s3Client, String bucketName){
        try {
            System.out.println("Deleting bucket: " + bucketName);

            s3Client.deleteBucket(dbr -> dbr.bucket(bucketName)); //dbr -> DeleteBucketRequest

            System.out.println(bucketName + " has been deleted.");

            System.out.printf("%n");

        }catch (S3Exception e){
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }

    }
}
