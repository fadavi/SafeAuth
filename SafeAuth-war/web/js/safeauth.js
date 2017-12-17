/* 
 * Copyright (C) 2017 Mohamad Fadavi <fadavi@fadavi.net>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
jQuery(function ($) {
    'use strict';

    var api = {
        BASE_URL: 'api/user/',
        SUCCESS: 1,

        ping: function () {
            return $.ajax({
                url: api.BASE_URL + 'ping',
                method: 'GET',
                dataType: 'json',
                cache: false
            });
        },

        requestAuthToken: function () {
            return $.ajax({
                url: api.BASE_URL + 'requestAuthToken',
                method: 'GET',
                dataType: 'json',
                cache: false
            });
        },

        requestRegisterToken: function () {
            return $.ajax({
                url: api.BASE_URL + 'requestRegisterToken',
                method: 'GET',
                dataType: 'json',
                cache: false
            });
        },

        _authenticate: function (userInfo) {
            return $.ajax({
                url: api.BASE_URL + 'authenticate',
                method: 'POST',
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(userInfo),
                cache: false
            });
        },

        authenticate: function (userInfo) {
            api.requestAuthToken()
                    .fail(app.onError)
                    .done(function (resp) {
                        app.log('Response:', resp);
                        if (resp.result === api.SUCCESS) {
                            var pem = resp.authToken;
                            var rsa = new JSEncrypt();
                            rsa.setPublicKey(pem);

                            userInfo.username = rsa.encrypt(userInfo.username);
                            userInfo.password = rsa.encrypt(userInfo.password);
                            
                            api._authenticate(userInfo)
                                    .fail(app.onError)
                                    .done(function (resp) {
                                        app.log(resp);
                                    });
                        }
                    });
        },

        _register: function (userInfo) {
            return $.ajax({
                url: api.BASE_URL + 'register',
                method: 'POST',
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(userInfo),
                cache: false
            });
        },

        register: function (userInfo) {
            api.requestRegisterToken()
                    .fail(app.onError)
                    .done(function (resp) {
                        app.log('Response: ', resp);
                        if (resp.result === api.SUCCESS) {
                            var pem = resp.registerToken;
                            var rsa = new JSEncrypt();
                            rsa.setPublicKey(pem);

                            userInfo.username = rsa.encrypt(userInfo.username);
                            userInfo.password = rsa.encrypt(userInfo.password);

                            api._register(userInfo)
                                    .fail(app.onError)
                                    .done(function (resp) {
                                        app.log(resp);
                                    });
                        }
                    });
        }
    };

    var app = {
        main: function () {
            app.$register = $('form#register');
            app.$authenticate = $('form#authenticate');
            app.$log = $('textarea#log');
            app.$regUsername = app.$register.find('input.username').first();
            app.$regPassword = app.$register.find('input.password').first();
            app.$authUsername = app.$authenticate.find('input.username').first();
            app.$authPassword = app.$authenticate.find('input.password').first();

            app.$register.submit(this.onRegisterSubmit);
            app.$authenticate.submit(this.onAuthenticateSubmit);
            $('input[type=button],input[type=submit]').click(app.onAnyButtonClick);
            $('button#ping').click(app.onPing);
            $('button#clear-log').click(app.onClearLog);
        },

        log: function () {
            app.$log.append('\n');
            for (var i = 0, len = arguments.length; i < len; i++) {
                var message = arguments[i];
                console.log(message);
                if (message.constructor && message.constructor === Object) {
                    message = JSON.stringify(message, null, 2).replace(/\"([^(\")"]+)\":/g, "$1:");
                    ;
                }
                app.$log.append(message + '\n');
            }
            app.$log.scrollTop(app.$log[0].scrollHeight);
        },

        onRegisterSubmit: function (e) {
            e.preventDefault();

            var userInfo = {
                username: app.$regUsername.val(),
                password: app.$regPassword.val()
            };
            app.$regPassword.val('');

            app.log(userInfo);

            api.register(userInfo);
        },

        onAuthenticateSubmit: function (e) {
            e.preventDefault();

            var userInfo = {
                username: app.$authUsername.val(),
                password: app.$authPassword.val()
            };
            app.$authPassword.val('');

            api.authenticate(userInfo);
        },

        onPing: function (e) {
            api.ping()
                    .fail(app.onError)
                    .done(function (resp) {
                        app.log('Response:', resp);
                    });
        },

        onClearLog: function () {
            app.$log.empty();
        },

        onError: function (_, status, error) {
            app.log('ERROR - status: ' + status + ' error: ' + error);
        },

        onAnyButtonClick: function () {
            app.log('You clicked: ' + $(this).val());
        }
    };

    app.main();
    app.log('Application started...');
});