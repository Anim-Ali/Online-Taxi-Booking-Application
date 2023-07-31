/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function allowComment(elmnt){
    const parent = elmnt.parentElement;
    const tds = parent.childNodes;
    let select = null;
    let input = null;
    tds.forEach(td => {
        if(td.tagName === 'TD'){
            const elm = td.childNodes;
            if(elm.length === 3 && elm[1].tagName === 'SELECT'){
                select = elm[1];
            }
            if(elm.length === 3 && elm[1].tagName === 'INPUT'){
                input = elm[1];
            }
        }
    })
    
    if(select.value === 'Reject'){
        input.removeAttribute("readonly");
        input.setAttribute('required', true);
        input.classList.remove('tbl-inputs');
        input.value = "";
    } else {
        input.setAttribute('readonly', true);
        input.removeAttribute('required')
        input.classList.add('tbl-inputs');
        input.value = "None";
    }
    
}