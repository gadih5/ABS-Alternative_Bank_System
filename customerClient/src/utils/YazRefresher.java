package utils;

import bank.Bank;
import javafx.beans.property.BooleanProperty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.TimerTask;
import java.util.function.Consumer;
import controller.constants.Constants;

import static controller.constants.Constants.GSON_INSTANCE;


public class YazRefresher extends TimerTask {

     //   private final Consumer<String> httpRequestLoggerConsumer;
        private final Consumer<String> yazConsumer;
        private int requestNumber;
        private final BooleanProperty shouldUpdate;



        public YazRefresher(BooleanProperty shouldUpdate,
                           // Consumer<String> httpRequestLoggerConsumer,
                            Consumer<String> yazConsumer) {
            this.shouldUpdate = shouldUpdate;
         //   this.httpRequestLoggerConsumer = httpRequestLoggerConsumer;
            this.yazConsumer =yazConsumer;
            requestNumber = 0;

        }

        @Override
        public void run() {

            if (!shouldUpdate.get()) {
                return;
            }

            final int finalRequestNumber = ++requestNumber;
        //    httpRequestLoggerConsumer.accept("About to invoke: " + Constants.BANK_SENDER + " | Users Request # " + finalRequestNumber);
            HttpClientUtil.runAsync( Constants.BANK_SENDER, new Callback() {

                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
         //           httpRequestLoggerConsumer.accept("Users Request # " + finalRequestNumber + " | Ended with failure...");

                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String BankString = response.body().string();
         //           httpRequestLoggerConsumer.accept("Users Request # " + finalRequestNumber + " | Response: " + BankString);
                    String yaz = GSON_INSTANCE.fromJson(BankString, String.class);
                    yazConsumer.accept(yaz);
                }
            });
        }
    }
