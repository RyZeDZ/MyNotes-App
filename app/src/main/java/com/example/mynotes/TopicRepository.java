package com.example.mynotes;
import android.content.Context;
import androidx.lifecycle.LiveData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
public class TopicRepository {
    private TopicDao topicDao;
    private FirebaseFirestore db;
    private String userId;
    public TopicRepository(Context context) {
        topicDao = AppDatabase.getInstance(context).topicDao();
        db = FirebaseFirestore.getInstance();        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) userId = user.getUid();
    }
    public LiveData<List<TopicEntity>> getAllTopics() {
        return topicDao.getAllTopics(userId);
    }
    public void insertTopic(TopicEntity topic) {
        topic.userId = userId;
        topic.id = UUID.randomUUID().toString();
        Executors.newSingleThreadExecutor().execute(() -> topicDao.insert(topic));
        db.collection("users").document(userId).collection("topics").document(topic.id).set(topic);
    }
    public void deleteTopic(TopicEntity topic) {
        Executors.newSingleThreadExecutor().execute(() -> topicDao.delete(topic));
        db.collection("users").document(userId).collection("topics").document(topic.id).delete();
    }
}