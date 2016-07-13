package com.trailblazers.freewheelers;

import org.junit.Ignore;
import org.junit.Test;

import static com.trailblazers.freewheelers.helpers.SyntaxSugar.ONLY_ONE_LEFT;
import static com.trailblazers.freewheelers.helpers.SyntaxSugar.SOME_PASSWORD;

public class PaymentTest extends UserJourneyBase{

    @Test
    public void shouldShowInvalidFieldErrorsWhenInvalidField() {
        user
                .opens_payment_page()
                .submits_payment_details();
        screen
                .should_see_error_in_card_form("Must enter a card number.")
                .should_see_error_in_card_form("Must enter a valid CCV.")
                .should_see_error_in_card_form("Must enter an expiration date.")
                .should_see_error_in_card_form("Must select card type.");
    }

    @Ignore
    @Test
    public void shouldShowReservePageWhenUsingValidCardDetails() throws Exception {

        String jan = "Jan Plewka";
        String type = "Visa";
        String card_no = "4111111111111111";
        String ccv = "534";
        String exp_month = "11";
        String exp_year = "2020";

        user
                .logs_in_with(jan, SOME_PASSWORD)
                .visits_home_page()
                .reservesAnItem()
                .checksOutItem()
                .entersPaymentDetails(type, card_no, ccv, exp_month, exp_year)
                .submits_payment_details();
        screen
                .shouldSeePaymentSuccess();
    }

    @Ignore
    @Test
    public void shouldShowReserveErrorPageWhenUsingInvalidCardDetails() throws Exception {

        String jan = "Jan Plewka";
        String type = "MasterCard";
        String card_no = "123";
        String ccv = "534";
        String exp_month = "11";
        String exp_year = "2020";

        user
                .logs_in_with(jan, SOME_PASSWORD)
                .visits_home_page()
                .reservesAnItem()
                .checksOutItem()
                .entersPaymentDetails(type, card_no, ccv, exp_month, exp_year)
                .submits_payment_details();
        screen
                .shouldSeePaymentFailure();
    }

    @Ignore
    @Test
    public void shouldShowReserveErrorPageWhenCardIsRevoked() throws Exception {

        String jan = "Jan Plewka";
        String type = "Visa";
        String card_no = "4111111111111116";
        String ccv = "534";
        String exp_month = "11";
        String exp_year = "2020";

        user
                .logs_in_with(jan, SOME_PASSWORD)
                .visits_home_page()
                .reservesAnItem()
                .checksOutItem()
                .entersPaymentDetails(type, card_no, ccv, exp_month, exp_year)
                .submits_payment_details();
        screen
                .shouldSeePaymentFailure();
    }
    @Test
    public void shouldShowReservePageWhenUsingValidCardDetailsWithoutCart() throws Exception {

        String jan = "Jan Plewka";
        String type = "Visa";
        String card_no = "4111111111111111";
        String ccv = "534";
        String exp_month = "11";
        String exp_year = "2020";
        String Simplon_Frame = "Simplon Pavo 3 Ultra " + System.currentTimeMillis();
        String Arno = "Arno Admin";


        admin
                .there_is_an_admin(Arno, SOME_PASSWORD)
                .there_is_a_frame(Simplon_Frame, ONLY_ONE_LEFT);

        user
                .logs_in_with(jan, SOME_PASSWORD)
                .visits_home_page();

        screen
                .should_list_item(Simplon_Frame);

        user
                .add_item_to_cart(Simplon_Frame);

        user
                .entersPaymentDetails(type, card_no, ccv, exp_month, exp_year)
                .submits_payment_details();
        screen
                .shouldSeePaymentSuccess()
                .shouldDisplayPurchasedItem(Simplon_Frame);

    }
}
