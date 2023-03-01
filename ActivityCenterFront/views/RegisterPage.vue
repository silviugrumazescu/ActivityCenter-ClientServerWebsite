<template>
    <div class="registerFormContainer">
        <Form name="registerForm" @submit="handleRegister">
            <h1>Register</h1>
            <div class="mb-3">
                <label for="firstnameInput" class="form-label">First name</label>
                <Field v-model="user.firstname" name="firstname" type="firstname" class="form-control" :rules="isRequired"/>
                <ErrorMessage name="firstname" as="p"/>
            </div>
            <div class="mb-3">
                <label for="lastnameInput" class="form-label">Last name</label>
                <Field v-model="user.lastname" name="lastname" type="lastname" class="form-control" id="lastnameInput" :rules="isRequired"/>
                <ErrorMessage name="lastname" />
            </div>
            <div class="mb-3">
                <label for="emailInput" class="form-label">Email</label>
                <Field v-model="user.email" name="email" type="email" class="form-control" id="emailInput" :rules="validateEmail"/>
                <ErrorMessage name="email" />
            </div>
            <div class="mb-3">
              <label for="usernameInput" class="form-label">Username</label>
              <Field v-model="user.username" name="username" type="username" class="form-control" id="usernameInput" :rules="isRequired"/>
              <ErrorMessage name="username" />
            </div>
            <div class="mb-3">
              <label for="passwordInput" class="form-label">Password</label>
              <Field v-model="user.password" name="password" type="password" class="form-control" id="passwordInput" :rules="isRequired"/>
              <ErrorMessage name="password" />
            </div>
            <div class="mb-3">
               <label for="passwordInput" class="form-label">Repeat password</label>
               <Field name="repeatPassword" type="password" class="form-control" id="passwordInput" :rules="confirmedPassword"/>
               <ErrorMessage name="repeatPassword" />
            </div>
            <div v-if="responseReceived" class="alert" :class="successful ? 'alert-success' : 'alert-danger'">
                <h3>{{this.message}}</h3>
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </Form>
    </div>
</template>

<script>
import {Field, Form, ErrorMessage} from 'vee-validate'
    import User from '../models/User'

    export default {
        name: "RegisterPage",

        data() {
            return {
                user: new User('','','','','',),
                successful: false,
                responseReceived: false,
                message: ''
            }
        },
        components: {
            Field, 
            Form,
            ErrorMessage
        },
        methods: {
            handleRegister() {
                this.$store.dispatch('auth/register', this.user).then(
                    data => {
                        this.message = data.message;
                        this.successful = true;
                        this.responseReceived = true;
                    },
                    error => {
                        this.message = error.response.data.message;
                        this.successful = false;
                        this.responseReceived = true;
                    } 
                )
                
            },
            validateEmail(value) {
                if (!value) {
                    return 'This field is required';
                }
                const regex = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i;
                if (!regex.test(value)) {
                    return 'This field must be a valid email';
                }
                return true;
            },
            isRequired(value) {
                if (!value) {
                    return 'This field is required';
                }
                return true;
            },
            confirmedPassword(value) {
                if (!value) {
                    return 'This field is required';
                }
                if (this.user.password != value) {
                    return 'Passwords do not match!'
                }
                return true;
            }
        }

    }
</script>

<style scoped>
    form {
        padding: 20px;
        border: 3px solid #f1f1f1;
    }

    .registerFormContainer {
        width: 50%;
        margin-left: auto;
        margin-right: auto;
    }

    h1 {
        text-align: center;
    }

    p {
        color: red;
    }

</style>