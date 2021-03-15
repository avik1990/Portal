package chatbots.retrofit;


import java.util.List;

import chatbots.model.AnswerResponse;
import chatbots.model.QuestionResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("chatbot_Questions?checksum=01091981")
    Call<List<QuestionResponse>> getAllQuestions();


    @GET("chatbot_Answers?checksum=01091981")
    Call<List<AnswerResponse>> getAnswerResponse(@Query("id") String ID);


}

