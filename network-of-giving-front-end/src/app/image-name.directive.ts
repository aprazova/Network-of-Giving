import { Directive, ElementRef, Renderer2, HostListener, Input} from '@angular/core';
import { fromEvent } from 'rxjs';
import { first } from 'rxjs/operators';
import { ImagePayload } from './add-charity/image';
import { FileService } from './services/file-service/file.service';

@Directive({
  selector: '[appImageName]'
})
export class ImageNameDirective {

  @Input() appImageName: string;
  constructor(private fileService: FileService, private renderer: Renderer2, private el: ElementRef) { }

  ngOnInit() {
    // this.setPlaceholder();
    this.setImage();

  }

  private setPlaceholder() {
    this.renderer.setAttribute(
      this.el.nativeElement,
      'src', `https://via.placeholder.com/250x150`
    )
  }
  private setImage = () => {

    this.fileService.getImage(this.appImageName).subscribe(
      (data: ImagePayload )=> {
        this.renderer.setAttribute(
          this.el.nativeElement,
          'src', data.body
        )
      }
    )
  }

}
