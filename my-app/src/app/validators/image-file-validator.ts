import {FormControl, ValidationErrors} from "@angular/forms";

export class ImageFileValidator {
    static invalidImageType(formControl: FormControl): ValidationErrors{
        if (!formControl.value) {
            return {};
        }
        const allowedExtensions = ['jpg', 'jpeg', 'png', 'gif'];
        const fileExtension = (formControl.value.split('.').pop()).toLowerCase();
        const isValidExtension = allowedExtensions.includes(fileExtension);
        if(!isValidExtension){
            return { 'invalidImageType': true}
        }
        return {}
    }
}
