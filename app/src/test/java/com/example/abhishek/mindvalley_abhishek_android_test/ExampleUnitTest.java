package com.example.abhishek.mindvalley_abhishek_android_test;

import android.content.Context;
import android.test.mock.MockContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.List;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {

    Context context;
    @Test
    public void serializationSuccessful() throws Exception {
        String jsonTestString = new MockContext().getResources().getString(R.string.json_test_string);
        Type type = new TypeToken<List<Board>>() {
        }.getType();
        List<Board> boards_actual=new Gson().fromJson(jsonTestString, type);
        byte[] bytes = jsonTestString.getBytes();
        List<Board> boards_test= BoardActivity.deserializeBytes(bytes);
        Assert.assertSame(boards_actual, boards_test);
        Assert.assertEquals(4, 2 + 2);

    }

    @Before
    public void setUp() throws Exception {
        context = new MockContext();
        Assert.assertNotNull(context);
    }

}