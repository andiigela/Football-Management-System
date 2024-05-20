import {FormControl, ValidationErrors} from "@angular/forms";

export class DateValidator {
  static NotValidExpectedRecoveryDate(formControl: FormControl):ValidationErrors{
    if(!formControl.value){
      return {};
    }
    let formDate = formControl.value;
    const dateArray = formDate.split('-');
    let currentDate = new Date();
    if(((parseInt(dateArray[0]) - currentDate.getFullYear()) < 0)){
      return {NotValidExpectedRecoveryDate: true}
    }
    if(dateArray[0].length > 4){
      return {NotValidExpectedRecoveryDate: true}
    }
    return {}
  }
  static NotValidCreationDate(formControl: FormControl):ValidationErrors{
    if(!formControl.value){
      return {};
    }
    let formDate = formControl.value;
    const dateArray = formDate.split('-');
    let currentDate = new Date();
    if((parseInt(dateArray[0]) < currentDate.getFullYear() - 1)){
      return {NotValidCreationDate: true}
    }
    if(dateArray[0].length > 4){
      return {NotValidCreationDate: true}
    }
    return {}
  }
}
