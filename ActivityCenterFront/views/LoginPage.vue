<template>
    <div class="loginFormContainer">
        <Form name="form" @submit="handleLogin">
            <h1>Login</h1>
            <div class="mb-3">
                <label for="usernameInput" class="form-label">Username</label>
                <Field v-model="user.username" name="username" type="username" class="form-control" id="usernameInput" :rules="isRequired"/>
                <ErrorMessage name="username" />
            </div>
            <div class="mb-3">
              <label for="passwordInput" class="form-label">Password</label>
              <Field v-model="user.password" name="password" type="password" class="form-control" id="passwordInput" :rules="isRequired"/>
              <ErrorMessage name="password"/>
            </div>
            <div v-if="message" class="alert alert-danger" >{{message}}</div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </Form>
    </div>
</template>

<script>

    import {Form, Field, ErrorMessage} from 'vee-validate';
    import User from '../models/User'

    export default {
        name: "LoginPage",
        data() {
            return {
                user: new User('','','','',''),
                message: ''
            }
        },

        methods: {
            handleLogin() {
                if(this.user.username && this.user.password) {
                    this.$store.dispatch('auth/login', this.user).then(
                        user => {
                            this.$router.push('/');
                        },
                        error => {
                            this.message = "Failed to login";
                        }
                    )
                }
            },
            isRequired(value) {
                if (!value) {
                    return 'This field is required!';
                }
                return true;
            }

        },
        components: {
            Form,
            Field,
            ErrorMessage
        }
    }
</script>

<style scoped>
    form {
        padding: 20px;
        border: 3px solid #f1f1f1;
    }

    .loginFormContainer {
        width: 50%;
        margin-left: auto;
        margin-right: auto;
    }

    h1 {
        text-align: center;
    }

</style>