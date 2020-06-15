import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'linesRange'
})
export class LinesRangePipe implements PipeTransform {

  transform(value: string, ...args: unknown[]): unknown {
    const start  = (args[0] ? args[0] : 0) as number;
    const end = (args[1] ? args[1]: value.length) as number; 
    let result: string = value.substring(start, end);
    
    if(end < value.length){
      result += " ...";
    }

    return result;
  }

}
