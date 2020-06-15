import { Pipe, PipeTransform } from '@angular/core';
import { CharityPayload } from '../add-charity/charity-payload';

@Pipe({
  name: 'charityFilter'
})
export class CharityFilterPipe implements PipeTransform {

  transform(charityPayloads: Array<CharityPayload>, searchTerm: string): Array<CharityPayload>{
    if(!charityPayloads || !searchTerm){
      return charityPayloads;
    }

    return charityPayloads.filter(charity => charity.title.toLocaleLowerCase().indexOf(searchTerm.toLocaleLowerCase()) !== -1);
  }

}
