package com.example.demo;

import android.content.Context;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.demo.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.extern.slf4j.Slf4j;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@Slf4j
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    private ActivityScenarioRule<MainActivity> rule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void before(){
        log.info("启动测试");
    }

    @Test
    public void useAppContext() {

        ActivityScenario<MainActivity> scenario = rule.getScenario();


        Lifecycle.State state = scenario.getState();
        log.info("当前组昂泰为: {}", state);

    }
}