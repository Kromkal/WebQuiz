<#import "parts/common.ftl" as e>
<#include "parts/security.ftl">

<@e.page>
<#--    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>-->
    <script src="/static/changePersonalInfo.js"></script>
    <script src="/static/changePassword.js"></script>
    <script src="/static/emailMessageForChangePassword.js"></script>

<#--    <form>-->
    <div class="form-row mt-2" id="profile">
        <div class="col-md-6 mb-3">
            <label for="validationDefault01">Имя</label>
            <input type="text" class="form-control" id="validationDefault01" value="${user.firstName}" name="firstName" required>
        </div>
        <div class="col-md-6 mb-3">
            <label for="validationDefault02">Фамилия</label>
            <input type="text" class="form-control" id="validationDefault02" value="${user.lastName}" name="lastName" required>
        </div>
        <div class="col-md-6 mb-3">
            <label for="validationDefault03">Почта</label>
            <input type="text" class="form-control" id="validationDefault03" disabled value="${user.email}" name="email" required>
        </div>
        <div class="col-md-6 mb-3">
            <label for="validationDefault04">Статус аккаунта</label>
            <input type="text" class="form-control" id="validationDefault04" disabled value="${user.status?c}" name="email" required>
        </div>


        <div class="col-md-6 mb-3">
            <form method="post" enctype="multipart/form-data" action="/upload">
                <label for="formFile" class="form-label">Фотография</label>
                <input class="form-control" type="file" id="formFile" name="file">
                <label for="test" class="mt-2">Нажмите чтобы загрузить</label>
                <button type="submit"  class="btn btn-primary form-control" id="test">Отправить</button>
            </form>
        </div>

        <div>

        <img width="100px" height="100px" src="/../../../../img/${user.avatar}.jpg" alt="../../../../img/${user.avatar}.jpg">



        </div>



    </div>

   <#-- <div>
        <video controls width="400" height="300" src="../../../../video/drownedInst.mp4"></video>
    </div>-->

    <div class="form-group">
        <label for="exampleInputEmail1">Логин</label>
        <input type="text" class="form-control" id="exampleInputEmail1" disabled value="${user.username}" aria-describedby="emailHelp" name="username">
        <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
    </div>


    <button onclick="changePersonalInfo()" id="confirm" type="submit" class="btn btn-primary ml-2 my-1">Подтвердить</button>


    <!-- Button trigger modal -->
    <button type="button" class="btn btn-primary ml-2 my-1" data-toggle="modal" data-target="#staticBackdrop">
        Изменить пароль
    </button>

    <div class="modal fade" id="staticBackdrop" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="staticBackdropLabel">Изменение пароля</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="form-group"> Вы действительно хотите изменить пароль?

<#--                        <input type="text" class="form-control" id="oldpassword" placeholder="Придумайте новый пароль"  aria-describedby="emailHelp" name="password">-->
                        <#--<input type="text" class="form-control" id="password1" placeholder="Придумайте новый пароль"  aria-describedby="emailHelp" name="password1">
                        <input type="text" class="form-control mt-3" id="password2" placeholder="Введите новый пароль ещё раз" aria-describedby="emailHelp" name="password2">-->
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Назад</button>
                    <button type="button" onclick="sendEmailMessage()" data-dismiss="modal" class="btn btn-primary">Да</button>
                </div>

            </div>
        </div>



    </div>





</@e.page>