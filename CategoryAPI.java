package APIInterface;


import Model.AppPartlist.AppPart;
import Model.ChangePassword.Changepassresult;
import Model.Companyrep.Companyrep;
import Model.Dealercity.Dealercitylist;
import Model.Dealerstate.Dealerstatelist;
import Model.Dealetstatefilter.Dealerstatefilter;
import Model.Distributercity.Distributorcity;
import Model.Distributerstate.Distributerstate;
import Model.Distributerstatefilter.Distributerstatefilter;
import Model.Forgot.Forgotresult;
import Model.FullUnitNumber.Fullunitnolist;
import Model.FullUnitsearch.Fullunitsearch;
import Model.FullUnitsearch.SerFullunitsearch;
import Model.Login.Loginmodel;
import Model.Modellist.Model;
import Model.OEcustomers.OEcustomers;
import Model.Partnolist.Partnolist;
import Model.Partnosearch.Partnosearch;
import Model.Productlist.Productlist;
import Model.Productsearch.Productsearch;
import Model.Rating.Ratinglist;
import Model.Register.Registerresult;
import Model.Segments.Segment;
import Model.SendEnquiry.Enquiry;
import Model.Servicedealer.Servicedealer;
import Model.Type.Typelist;
import Model.Voltage.Voltagelist;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


/**
 * Created by system9 on 10/16/2017.
 */
public interface CategoryAPI {
    @FormUrlEncoded
    @POST("Getservicedealer")
    Call<Servicedealer> servicedealer(
            @Field("Email_id") String Email_id
    );
    @FormUrlEncoded
    @POST("Getcompanyuser")
    Call<Companyrep> companyrep(
            @Field("emailid") String emailid
    );

    @FormUrlEncoded
    @POST("Register")
    Call<Registerresult> register(
            @Field("Name") String Name,
            @Field("Email_id") String Email_id,
            @Field("MobileNo") String MobileNo,
            @Field("Company") String Company,
            @Field("City") String City,
            @Field("Address") String Address,
            @Field("Country") String Country,
            @Field("Pincode") String Pincode,
            @Field("Usertype") String Usertype
    );
    @FormUrlEncoded
    @POST("Login")
    Call<Loginmodel> logint(
            @Field("Email_id") String Email_id,
            @Field("Password") String Password
    );

    @FormUrlEncoded
    @POST("Changepassword")
    Call<Changepassresult> changepassword(
            @Field("Email_id") String Email_id,
            @Field("Password") String Password,
            @Field("Newpassword") String Newpassword
    );
    @FormUrlEncoded
    @POST("Forgotpassword")
    Call<Forgotresult> forgotpassword(
            @Field("Email_id") String Email_id
    );

    @GET("Partnolist")
    Call<Partnolist> PartNumberlist();

    @FormUrlEncoded
    @POST("Partsearch")
    Call<Partnosearch> Partnosearch(
            @Field("PartNumber") String PartNumber
    );

    @GET("FullUnitPartnolist")
    Call<Fullunitnolist> fullunitlist();

    @FormUrlEncoded
    @POST("FullunitPartsearch")
    Call<Fullunitsearch> fullsearchlist(
            @Field("PartNumber") String PartNumber
    );
    @FormUrlEncoded
    @POST("FullunitPartsearch")
    Call<SerFullunitsearch> serfullsearchlist(
            @Field("PartNumber") String PartNumber
    );
    @GET("Productlist")
    Call<Productlist> PRODUCTLIST_CALL();

    @FormUrlEncoded
    @POST("PartOutput")
    Call<Ratinglist> ratinglist(@Field("Productname") String Productname,
                                @Field("Part_Volt") String Part_Volt);

    @FormUrlEncoded
    @POST("PartVolt")
    Call<Voltagelist> voltagelist(@Field("Productname") String Productname);

    @FormUrlEncoded
    @POST("PartType")
    Call<Typelist> typelist(@Field("Productname") String Productname);

    @FormUrlEncoded
    @POST("Productsearch")
    Call<Productsearch> productsearch(
            @Field("Productname") String Productname,
            @Field("Part_Outputrng") String Part_Outputrng,
            @Field("Pro_type") String Pro_type,
            @Field("Part_Volt") String Part_Volt
    );


    @GET("Segments")
    Call<Segment> segmentlist();

    @FormUrlEncoded
    @POST("OEcustomers")
    Call<OEcustomers> oecustomerlist(
            @Field("segmentss") String segmentss
    );

    @FormUrlEncoded
    @POST("Model")
    Call<Model> modellist(
            @Field("segment") String segment,
            @Field("make") String make
    );

    @FormUrlEncoded
    @POST("Partlist")
    Call<AppPart> apppartlist(
            @Field("segment") String segment,
            @Field("make") String make,
            @Field("model") String model
    );

    @GET("Dealerstate")
    Call<Dealerstatelist> dealerstate();

    @FormUrlEncoded
    @POST("Dealerstatefilter")
    Call<Dealerstatefilter> dealerstatefilter(
            @Field("State") String State

    );

    @FormUrlEncoded
    @POST("DealerCity")
    Call<Dealercitylist> dealercity(
            @Field("State") String State

    );

    @FormUrlEncoded
    @POST("Dealercityfilter")
    Call<Dealerstatefilter> dealercityfilter(
            @Field("State") String State,
            @Field("City") String City

    );

    @GET("Distributerstate")
    Call<Distributerstate> distributerstate();

    @FormUrlEncoded
    @POST("Distributerstatefilter")
    Call<Distributerstatefilter> distributerstatefilter(
            @Field("State") String State

    );

    @FormUrlEncoded
    @POST("Distributercity")
    Call<Distributorcity> distributercity(
            @Field("state") String State

    );

    @FormUrlEncoded
    @POST("Distributercityfilter")
    Call<Distributerstatefilter> distributercityfilter(
            @Field("state") String State,
            @Field("city") String City

    );

    @FormUrlEncoded
    @POST("SendEnquiry")
    Call<Enquiry> enquiry(
            @Field("name") String name,
            @Field("emailid") String emailid,
            @Field("mobileno") String mobileno,
            @Field("Partno") String Partno,
            @Field("message") String message,
            @Field("city") String city,
            @Field("company") String company,
            @Field("contacttype") String contacttype

    );
}
