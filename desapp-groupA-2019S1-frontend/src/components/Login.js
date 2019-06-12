import React from "react";
import {auth} from "../components/Root.js";
// i18next Hook
import { withTranslation } from 'react-i18next';
import i18next from "i18next";

const Login = ({t},props) => (
    <div>
      <button onClick={ () => i18next.changeLanguage('en_US') }>{t('englishLanguage')}</button>
      <button onClick={ () => i18next.changeLanguage('es_AR') }>{t('spanishLanguage')}</button>
      <br/>
      {t('loginPage->slogan')}
      <br/>
      {t('loginPage->description')}
      <br/>
      <button onClick={ () => auth.login() }>{t('loginPage->button')}</button>
    </div>
);

export default withTranslation ()(Login);