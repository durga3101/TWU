package com.trailblazers.freewheelers.apis;

import com.trailblazers.freewheelers.helpers.ManageItemTable;
import com.trailblazers.freewheelers.helpers.OrderTable;
import com.trailblazers.freewheelers.helpers.URLs;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

import static com.trailblazers.freewheelers.helpers.Controls.*;
import static com.trailblazers.freewheelers.helpers.SyntaxSugar.*;

public class UserApi {

    private final String mainPageWindowHandle;
    private WebDriver driver;

    public UserApi(WebDriver driver) {
        this.driver = driver;
        this.mainPageWindowHandle = driver.getWindowHandle();
    }

    public UserApi is_logged_out() {
        driver.get(URLs.logout());
        driver.findElement(By.linkText("Logout")).click();
        return this;
    }

    public UserApi logs_in_with(String userName, String userPassword) {
        driver.get(URLs.login());

        new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.name("j_username")));
        driver.findElement(By.name("j_username")).sendKeys(userName);
        driver.findElement(By.name("j_password")).sendKeys(userPassword);

        driver.findElement(By.name("submit")).click();

        return this;
    }

    public UserApi creates_an_account(String name, String email, String password, String confirmPassword, String phoneNumber, String country) {
        driver.get(URLs.home());
        driver.findElement(By.linkText("Create Account")).click();

        fillField(driver.findElement(By.id("fld_email")), email);
        fillField(driver.findElement(By.id("fld_password")), password);
        fillField(driver.findElement(By.id("fld_confirmPassword")), confirmPassword);
        fillField(driver.findElement(By.id("fld_name")), name);
        fillField(driver.findElement(By.id("fld_phoneNumber")), phoneNumber);

        Select select = new Select(driver.findElement(By.id("fld_country")));
        select.selectByVisibleText(country);


        driver.findElement(By.id("createAccount")).click();

        return this;
    }

    public UserApi creates_an_item(String name, String type, String quantity, String price, String description, String imgUrl) {
        fillField(driver.findElement(By.id("name")), name);
        fillField(driver.findElement(By.id("price")), price);

        Select select = new Select(driver.findElement(By.id("type")));
        select.selectByVisibleText(type);

        fillField(driver.findElement(By.id("description")), description);
        fillField(driver.findElement(By.id("quantity")), quantity);
        fillField(driver.findElement(By.id("imageURL")), imgUrl);

        driver.findElement(By.id("createItem")).click();

        return this;
    }

    public UserApi visits_his_profile() {
        driver.findElement(By.linkText("Profile")).click();
        return this;
    }

    public UserApi visits_admin_profile() {
        driver.findElement(By.linkText("Admin")).click();
        return this;
    }

    public UserApi visits_nps_report_page() {
        driver.findElement(By.linkText("NPS Report")).click();
        return this;
    }

    public UserApi visits_nps_report_page_by_link() {
        driver.get(URLs.surveyReport());
        return this;
    }

    public UserApi visits_profile_for(String name) {
        driver.get(URLs.userProfile() + "/" + encoded(name));
        return this;
    }

    public UserApi visits_home_page() {
        driver.get(URLs.home());
        return this;
    }

    public UserApi visits_payment_page() {
        driver.get(URLs.payment());
        return this;
    }

    public UserApi wants_to_manage_items() {
        driver.get(URLs.admin());
        driver.findElement(By.id("manageItems")).click();
        return this;
    }

    public UserApi changes_item_name(String from, String to) {
        check(driver.findElement(ManageItemTable.toggleAll()));

        WebElement input = driver.findElement(ManageItemTable.nameFieldFor(from));
        fillField(input, to);

        driver.findElement(By.name("update")).click();

        return this;
    }

    public UserApi changes_item_quantity(String from, String to) {
        check(driver.findElement(ManageItemTable.toggleAll()));

        WebElement input = driver.findElement(ManageItemTable.quantityFieldFor(from));
        fillField(input, to);

        driver.findElement(By.name("update")).click();

        return this;
    }

    public UserApi changes_item_price(String from, String to) {
        check(driver.findElement(ManageItemTable.toggleAll()));

        WebElement input = driver.findElement(ManageItemTable.priceFieldFor(from));
        fillField(input, to);

        driver.findElement(By.name("update")).click();

        return this;
    }


    public UserApi delete_item(String itemName) {
        check(driver.findElement(ManageItemTable.checkBoxFor(itemName)));
        driver.findElement(By.name("delete")).click();

        return this;
    }

    public UserApi add_to_cart_and_check_out(String name) {
        add_item_to_cart(name);
        reserve_items_in_shopping_cart();
        //proceed we have to add credit card
        return this;

    }

    public UserApi reserve_items_in_shopping_cart() {
        driver.findElement(By.id("checkout")).click();
        return this;
    }

    public UserApi add_item_to_cart(String name) {
        driver.findElement(By.id("add_item"+name)).click();
        return this;
    }

    public UserApi changes_order_status(String item, String toState) {
        select(toState, driver.findElement(OrderTable.selectFor(item)));
        driver.findElement(OrderTable.saveButtonFor(item)).click();
        return this;
    }

    private String encoded(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public UserApi waits_for_survey_popup() throws InterruptedException {
        waitForSurveyToShow();
//        focusOnPopUpWindow();
        return this;
    }

    private void focusOnPopUpWindow() {
        Iterator<String> handleIterator = driver.getWindowHandles().iterator();
        String popupHandle = handleIterator.next();
        popupHandle = popupHandle.equals(mainPageWindowHandle) ? handleIterator.next() : popupHandle;
        driver.switchTo().window(popupHandle);
    }

    private void waitForSurveyToShow() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("modal")));
    }

    public void openCreateAccountPage() {
        driver.get(URLs.home());

        driver.findElement(By.linkText("Create Account")).click();
    }

    public UserApi click_checkout_button() {
        driver.findElement(By.id("checkout")).click();
        return this;
    }

    public UserApi click_cancel_button() {
        driver.findElement(By.id("cancel")).click();

        return this;
    }

    public UserApi opens_payment_page() {
        driver.get(URLs.payment());

        return this;
    }

    public UserApi submits_payment_details() {
        driver.findElement(By.id("submit")).click();
        return this;
    }

    public UserApi reservesAnItem() {
        driver.findElement(By.className("add-to-cart")).click();
        return this;
    }

    public UserApi checksOutItem() {
        String pageText = driver.findElement(By.tagName("body")).getText();
        System.err.println("PAGETEXT: " + pageText);
        driver.findElement(By.id("checkout")).click();
        return this;
    }

    public UserApi entersPaymentDetails(String type, String card_no, String cvc, String exp_month, String exp_year) {
        Select selectType = new Select(driver.findElement(By.id("card_type")));
        selectType.selectByVisibleText(type);
        fillField(driver.findElement(By.id("card_number")), card_no);
        fillField(driver.findElement(By.id("card_ccv")), cvc);
        Select selectMonth = new Select(driver.findElement(By.id("date_month")));
        selectMonth.selectByVisibleText(exp_month);
        Select selectYear = new Select(driver.findElement(By.id("date_year")));
        selectYear.selectByVisibleText(exp_year);
        return this;
    }

    public UserApi visits_cart_page() {
        driver.get(URLs.shoppingCart());

        return this;
    }


    public UserApi entersShippingAddressDetails(String street1, String street2, String city, String state, String postcode) {
        fillField(driver.findElement(By.id("fld_street1")), street1);
        fillField(driver.findElement(By.id("fld_street2")), street2);
        fillField(driver.findElement(By.id("fld_city")), city);
        fillField(driver.findElement(By.id("fld_state")), state);
        fillField(driver.findElement(By.id("fld_postcode")), postcode);

        return this;
    }

    public UserApi clearCookies() {
        driver.manage().deleteAllCookies();

        return this;
    }

    public UserApi click_proceed_to_payment_button() {
        driver.findElement(By.id("proceedToPayment")).click();

        return this;
    }

    public UserApi click_payment_button() {
        driver.findElement(By.id("makePayment")).click();

        return this;
    }

    public UserApi click_submit_button() {
        driver.findElement(By.id("surveySubmitButton")).click();

        return this;
    }


    public UserApi clicks_back_to_checkout_button() {
        driver.findElement(By.id("back_to_checkout")).click();

        return this;
    }

    public void back_to_shopping_cart_page() {
        driver.findElement(By.id("back_to_cart")).click();

    }

    public void clicks_on_user_name_in_order_table(String userName) {
        driver.findElement(By.linkText(userName)).click();
    }

    public UserApi continueShopping() {
        driver.findElement(By.id("add-items")).click();
        return this;
    }

    public UserApi click_remove_from_cart_button(String itemName) {
        driver.findElement(By.id("remove" + itemName)).click();
        return this;
    }

    public UserApi clicksButtonWithId(String id) {
        driver.findElement(By.id(id)).click();
        return this;
    }

    public UserApi switchToInvoiceWindow() {
        try {
            String oldWinHandle = driver.getWindowHandle();
            for (String newWinHandle : driver.getWindowHandles()) {
                if (!oldWinHandle.equals(newWinHandle)) driver.switchTo().window(newWinHandle);
            }
        } catch (Exception e) {
            System.out.println("Unable to Switch Window:\n" + e.getMessage());
        }
        return this;
    }

    public UserApi closesWindow() {
        driver.close();
        return this;
    }


    public UserApi changes_item_image_url(String itemName, String imageUrl) {
        check(driver.findElement(ManageItemTable.toggleAll()));


        WebElement input = driver.findElement(By.id("image_url" + itemName));

        fillField(input, imageUrl);

        driver.findElement(By.name("update")).click();
        return this;
    }

    public UserApi switchToReservePage() {

        try {
            String oldWinHandle = driver.getWindowHandle();
            System.out.println(driver.getWindowHandle() + "\n\n\n\n\n\n\n\nWin&");
            if (driver.getCurrentUrl().equals(URLs.invoice())) {
                driver.findElement(By.id("close-invoice")).click();
            }
            for (String newWinHandle : driver.getWindowHandles()) {
                if (!oldWinHandle.equals(newWinHandle)) driver.switchTo().window(newWinHandle);
            }
        } catch (Exception e) {
            System.out.println("Unable to Switch Window:\n" + e.getMessage());
        }

        return this;
    }


    public UserApi waitsForInvoice() {
        WebDriverWait wait = new WebDriverWait(driver, 5000L);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("customer-invoice"))));
//        wait.withTimeout(seconds, TimeUnit.SECONDS);
        return this;
    }

    public UserApi create_user_login_and_buy_item_and_view_invoice_as_user(String userName, String email, String password, String country, String item) {
        this.is_logged_out()
                .creates_an_account(userName,
                        email,
                        password,
                        password,
                        SOME_PHONE_NUMBER,
                        country
                )
                .logs_in_with(email, password)
                .visits_home_page()
                .add_item_to_cart(item)
                .visits_cart_page()
                .click_checkout_button()
                .entersShippingAddressDetails(ADDRESS_1,
                        ADDRESS_2,
                        CITY,
                        STATE,
                        POSTAL_CODE
                )
                .click_proceed_to_payment_button()
                .entersPaymentDetails(VISA,
                        VALID_CARD_NO,
                        CCV,
                        EXP_MONTH,
                        EXP_YEAR
                )
                .click_payment_button()
                .click_submit_button()
                .click_cancel_button()
                .clicksButtonWithId(VIEW_INVOICE_BUTTON)
                .switchToInvoiceWindow();

        return this;
    }
}
