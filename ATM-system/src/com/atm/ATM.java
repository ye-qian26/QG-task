package com.atm;


import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ATM {
    private ArrayList<Account> accounts = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);
    Account loginAcc = new Account();

    /*1、欢迎页的设计，也就是设计start方法*/
    public void start(){
        while (true) {
            System.out.println("===欢迎您进入ATM系统===");
            System.out.println("1、用户登录");
            System.out.println("2、用户开户");
            System.out.println("请选择：");
            int command = sc.nextInt();
            switch (command) {
                case 1:
                    //用户登录
                    login();
                    break;
                case 2:
                    //用户开户
                    newAccount();
                    break;
                default:
                    System.out.println("您输入的指令有误！");
            }
        }
    }

    /*2、设计完成用户开户的方法*/
    private void newAccount(){
        System.out.println("====系统开户操作====");
        //1、创建一个Account对象来封装数据
        Account acc = new Account();

        //2、接收用户信息
        System.out.println("请输入您的账户名称：");
        String name = sc.next();
        acc.setUserName(name);

        while (true) {
            System.out.println("请输入您的性别：");
            char sex = sc.next().charAt(0);  //“男”
            if(sex == '男' || sex == '女'){
                acc.setSex(sex);
                break;
            }else{
                System.out.println("您输入的性别有误！只能是男或女！请重新输入！");
            }
        }

        while (true) {
            System.out.println("请输入您的密码：");
            String pwd = sc.next();
            System.out.println("请再次输入您的密码：");
            String pwd2 = sc.next();
            if(pwd.equals(pwd2)){
                acc.setPassword(pwd);
                break;
            }else{
                System.out.println("您两次输入的密码不一致！请重新输入！");
            }
        }

        System.out.println("请输入您的取现额度：");
        double limit = sc.nextDouble();
        acc.setLimit(limit);

        //重点：为账户生成一个卡号（由系统自动生成，8位数字，且不能与其他卡号重复）
        String cardId = createCardId();
        acc.setCardId(cardId);

        //3、将Account对象存入ArrayList中
        accounts.add(acc);
        System.out.println("恭喜您，" + acc.getUserName() + "开户成功，您的卡号是：" + acc.getCardId());
    }

    /*设计方法完成登录操作*/
    private void login(){
        //1、如果系统还没有任何卡号，直接退出登录操作
        if(accounts.size() == 0){
            System.out.println("系统中还未有任何账户！可前往开户操作~");
            return;
        }

        System.out.println("===系统登录===");
        //2、系统中存在卡号，开始完成登录操作
        while (true) {
            System.out.println("请输入您的卡号：");
            String cardId = sc.next();
            Account acc = AccountByCardId(cardId);
            //判断卡号是否存在
            if(acc == null){
                System.out.println("您输入的卡号不存在！请确认！");
            }else{
                //卡号存在进入输入密码操作
                while (true) {
                    System.out.println("请输入登录密码：");
                    String password = sc.next();
                    if(password.equals(acc.getPassword())){
                        loginAcc = acc;
                        System.out.println("恭喜您，" + acc.getUserName() + "登录成功了！您的卡号是：" + acc.getCardId());
                        //......
                        showUsersCommand();
                        return;//跳出当前方法
                    }else{
                        System.out.println("您输入的密码错误！请重新输入！");
                    }
                }
            }
        }
    }

    /*设计方法展示用户登录后的界面*/
    private void showUsersCommand(){
        while (true) {
            System.out.println("===" + loginAcc.getUserName() + "您可以选择如下操作进行账户的处理===");
            System.out.println("1、查询账户");
            System.out.println("2、存款");
            System.out.println("3、取款");
            System.out.println("4、转账");
            System.out.println("5、密码修改");
            System.out.println("6、退出");
            System.out.println("7、注销当前账户");
            System.out.println("请输入您的指令：");
            int command = sc.nextInt();
            switch (command) {
                case 1:
                    //查询账户
                    showLoginAccount();
                    break;
                case 2:
                    //存款
                    depositMoney();
                    break;
                case 3:
                    //取款
                    drawMoney();
                    break;
                case 4:
                    //转账
                    transforMoney();
                    break;
                case 5:
                    //密码修改
                    changePassword();
                    return;
                case 6:
                    //退出
                    System.out.println(loginAcc.getUserName() + "您退出系统成功！");
                    return;//跳出当前方法
                case 7:
                    //注销当前账户
                    if(deleteAccount()){
                        //回到欢迎页
                        return;
                    }
                    //回到操作页
                    break;
                default:
                    System.out.println("对不起，您输入的指令有问题！请重新输入！");

            }
        }
    }

    /*密码修改*/
    private void changePassword() {
        System.out.println("===账户密码修改操作===");
        while (true) {
            System.out.println("请您输入当前账户的密码：");
            String password = sc.next();

            if(password.equals(loginAcc.getPassword())){
                //输入密码正确
                System.out.println("请您输入新密码：");
                String newPassword1 = sc.next();

                while (true) {
                    System.out.println("请您再次确认新密码：");
                    String newPassword2 = sc.next();

                    if(newPassword1.equals(newPassword2)){
                        //密码修改成功
                        loginAcc.setPassword(newPassword1);
                        System.out.println("密码修改成功！请您重新登录~~");
                        return;
                    }else{
                        //再次输入时发生错误
                        System.out.println("两次密码不一致！请确认！");
                    }
                }
            }else{
                //输入密码错误
                System.out.println("您输入的密码错误！请确认！");
            }
        }
    }

    /*销户*/
    private boolean deleteAccount() {
        System.out.println("==注销账户操作");
        //1、确认用户是否真正销户
        System.out.println("请问您确定要注销该账户吗？y/n");
        String command = sc.next();
        switch (command) {
            case "y":
                //判断账户内是否有钱
                if(loginAcc.getMoney() == 0) {
                    //真正销户
                    accounts.remove(loginAcc);
                    System.out.println("您好，您的账户已成功销户~~");
                    return true;
                }else{
                    System.out.println("对不起！您的账户中还有金额，无法销户~~");
                    return false;
                }
            default:
                System.out.println("好的，您的账户保留~~");
                return false;

        }
    }

    /*转账*/
    private void transforMoney() {
        //1、前提：系统有至少两个账户
        if(accounts.size() < 2){
            System.out.println("当前系统中只有您一个账户，无法转账~~");
            return;
        }
        //2、判断自己账户是否有钱
        else if(loginAcc.getMoney() == 0) {
            System.out.println("您自己都没钱了~无法给其他账户转账~~");
            return;
        }else{
            //前两个条件均满足了
            while (true) {
                System.out.println("==转账操作==");
                System.out.println("请输入对方的卡号：");
                String cardId = sc.next();

                //3、调用已有函数查找卡号，判断卡号是否存在
                Account acc = AccountByCardId(cardId);
                if (acc == null) {
                    System.out.println("您输入的对方的卡号不存在~~");
                }else {
                    while (true) {
                        //4、开始认证姓氏
                        String name = "*" + acc.getUserName().substring(1);
                        System.out.println("请输入对方【" + name + "】的姓氏");
                        String preName = sc.next();
                        if(acc.getUserName().startsWith(preName)){
                            //认证成功：真正转账
                            while (true) {
                                System.out.println("请输入你要转账的金额");
                                double money = sc.nextDouble();
                                if(money <= loginAcc.getMoney()){
                                    //转账操作

                                    //更新自己账户的余额
                                    loginAcc.setMoney(loginAcc.getMoney() - money);
                                    //更新对方账户的余额
                                    acc.setMoney(acc.getMoney() + money);

                                    System.out.println("转账成功！");
                                    return;//跳出转账方法
                                }else {
                                    System.out.println("余额不足,您最多可转账的金额为：" + loginAcc.getMoney());
                                }
                            }
                        }else{
                            //认证失败
                            System.out.println("您的姓氏认证有问题！请重新输入！");
                        }
                    }
                }
            }
        }
    }

    /*取款*/
    private void drawMoney() {
        //1、判断用户余额是否大于100元，不大于则不让取款
        if(loginAcc.getMoney() < 100){
            System.out.println("您当前账户的余额不足100元！无法取款！可前往存款~~");
            return;
        }else{
            while (true) {
                System.out.println("==取款操作==");
                System.out.println("请输入您要取款的金额：");
                double money = sc.nextDouble();

                //2、判断取现金额是否超过取现额度
                if(money > loginAcc.getLimit()){
                    System.out.println("您输入的金额大于每次取现额度！您的取现额度为：" + loginAcc.getLimit());
                }else{

                    //3、判断账户金额是否足够
                    if(money <= loginAcc.getMoney()){
                        loginAcc.setMoney(loginAcc.getMoney() - money);
                        System.out.println("您取款" + money + "成功！取款后您剩余：" + loginAcc.getMoney());
                        break;
                    }else{
                        //余额不足
                        System.out.println("余额不足！您的当前余额仅为：" + loginAcc.getMoney());
                    }
                }
            }
        }
    }

    /*存款*/
    private void depositMoney() {
        System.out.println("==存款操作==");
        System.out.println("请输入您存款的金额：");
        double money = sc.nextDouble();
        loginAcc.setMoney(loginAcc.getMoney() + money);
        System.out.println(loginAcc.getUserName() + "恭喜您存款成功！当前账户的余额是：" + loginAcc.getMoney() );
    }

    /*展示用户账户的信息（查询账户）*/
    private void showLoginAccount(){
        System.out.println("===当前您的账户信息如下：===");
        System.out.println("卡号：" + loginAcc.getCardId());
        System.out.println("户主：" + loginAcc.getUserName());
        System.out.println("性别：" + loginAcc.getSex());
        System.out.println("余额：" + loginAcc.getMoney());
        System.out.println("取现额度：" + loginAcc.getLimit());
    }

    /*设计方法为账户生成一个8位的卡号,并返回该卡号*/
    private String createCardId(){
        while (true) {
            String cardId = "";
            Random r = new Random();
            for (int i = 0; i < 8; i++) {
                int data = r.nextInt(10);
                cardId += data;
            }
            Account acc = AccountByCardId(cardId);
            if(acc == null){
                return cardId;
            }
        }
    }

    /*设计一个方法通过卡号查询账户并返回该账户*/
    private Account AccountByCardId(String cardId){
        for (int i = 0; i < accounts.size(); i++) {
            Account acc = accounts.get(i);
            if(cardId.equals(acc.getCardId())){
                return acc;
            }
        }
        return null;
    }

}
