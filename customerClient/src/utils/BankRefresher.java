package utils;

import bank.Bank;
import javafx.beans.property.BooleanProperty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;
import java.util.function.Consumer;
import controller.constants.Constants;
import com.google.gson.Gson;
import javafx.beans.property.BooleanProperty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;
import java.util.function.Consumer;

import static controller.constants.Constants.GSON_INSTANCE;


public class BankRefresher extends TimerTask {

        private final Consumer<String> httpRequestLoggerConsumer;
        private final Consumer<Bank> bankConsumer;
        private int requestNumber;
        private final BooleanProperty shouldUpdate;



        public BankRefresher(BooleanProperty shouldUpdate, Consumer<String> httpRequestLoggerConsumer, Consumer<Bank> bankConsumer) {
            this.shouldUpdate = shouldUpdate;
            this.httpRequestLoggerConsumer = httpRequestLoggerConsumer;
            this.bankConsumer =bankConsumer;
            requestNumber = 0;

        }

        @Override
        public void run() {

            if (!shouldUpdate.get()) {
                return;
            }

            final int finalRequestNumber = ++requestNumber;
            httpRequestLoggerConsumer.accept("About to invoke: " + Constants.UPDATE_ALL + " | Users Request # " + finalRequestNumber);
            HttpClientUtil.runAsync( Constants.UPDATE_ALL, new Callback() {

                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    httpRequestLoggerConsumer.accept("Users Request # " + finalRequestNumber + " | Ended with failure...");

                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String BankString = response.body().string();
                    httpRequestLoggerConsumer.accept("Users Request # " + finalRequestNumber + " | Response: " + BankString);
                   Bank myBank = GSON_INSTANCE.fromJson(BankString, Bank.class);
                    bankConsumer.accept(myBank);
                }
            });
        }
    }
