import i18n from "i18next";
import Backend from "i18next-xhr-backend";
import LanguageDetector from 'i18next-browser-languagedetector';
import { initReactI18next } from "react-i18next";

i18n
  // detect user language
  // learn more: https://github.com/i18next/i18next-browser-languageDetector
  .use(LanguageDetector)
  // load translation using xhr -> see /public/locales
  // learn more: https://github.com/i18next/i18next-xhr-backend
  .use(Backend)
  .use(initReactI18next) // passes i18n down to react-i18next
  .init({
    fallbackLng: "es_AR", // is the default language that show if the language of the user it's not available.
    keySeparator: "->", // is for specify if we use the key separator. If don't need keyseparator put false.
    debug: false, // put true for see logs on chrome inspector.
    interpolation: {
      escapeValue: false // react already safes from xss
    },
    // special options for react-i18next
    // learn more: https://react.i18next.com/components/i18next-instance
    react: {
      wait: true
    }
  });

export default i18n;