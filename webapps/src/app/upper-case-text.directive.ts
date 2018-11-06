import { Directive, HostListener, Renderer, ElementRef } from '@angular/core';

@Directive({
  selector: '[appUpperCaseText]',
})

export class UpperCaseTextDirective {

  constructor(
    private renderer: Renderer,
    private el: ElementRef
  ) { }

  @HostListener('keyup') onKeyUp() {
    this.el.nativeElement.value = this.el.nativeElement.value.toUpperCase();
  }

}